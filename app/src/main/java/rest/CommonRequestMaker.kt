package rest

import android.app.Dialog
import com.appstyx.authtest.R
import com.appstyx.authtest.ui.AuthTestApplication
import okhttp3.RequestBody
import rest.restmodels.CommonResponse
import rest.restutils.ApiException
import rest.restutils.GsonUtils
import retrofit2.Response
import java.io.BufferedReader
import java.io.Reader


suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
    val response = call.invoke()
    if (response.isSuccessful) {
        return response.body()!!
    } else {
        val error = getExceptionFromResponse(response)

        if (error is CommonResponse) {
            throw   ApiException(error)
        } else {
            val commonResponse = CommonResponse()
            commonResponse.message = error.toString()
            throw ApiException(commonResponse)
        }

    }
}


fun <T> getExceptionFromResponse(response: Response<T>): Any {

//    val url = response.raw().request().url().toString()
//    val responseCode = response.code()

    var errorParsed: Any? = null
    var errorString: String? = null

    var error: Reader? = null
    try {
        error = response.errorBody()?.charStream()

        error?.let {
            errorString = readerToString(error)
            val commonResponse =
                GsonUtils.fromJson(errorString, CommonResponse::class.java) as CommonResponse
            errorParsed = commonResponse
        }

    } catch (e: Exception) {
        e.printStackTrace()

    }

//    logServerErrorEvent(response, errorString)

    return errorParsed ?: AuthTestApplication.getContext()
        .getString(R.string.err_msg_something_went_wrong)

}


private fun requestBodyToString(requestBody: RequestBody): String {
    try {
        val buffer = okio.Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return requestBody.toString()
}

fun readerToString(reader: Reader): String {
    try {
        val `in` = BufferedReader(reader)
        var line: String? = null
        val rslt = StringBuilder()
        while (`in`.readLine().also { line = it } != null) {
            rslt.append(line)
        }
        return rslt.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return reader.toString()
}
