package rest

import android.util.Base64
import com.appstyx.authtest.BuildConfig
import com.appstyx.authtest.common.PreferencesUtils
import com.appstyx.authtest.ui.AuthTestApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rest.restutils.GsonUtils
import rest.restutils.NetworkConnectionInterceptor
import rest.restutils.ToStringConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {

    var BASE_URL = BuildConfig.BASE_URL

    private const val CONNECTION_TIME_OUT: Long = 10


    fun getRetrofitService(): RetrofitService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)   // if you do not want to use this Base url then , give complete path in service , This base url will be ignored  @Source https://stackoverflow.com/a/34844301/6478047
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getInterceptorClient())

//        if (!forDownload) {
        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())  ////for making string request https://stackoverflow.com/a/42489802/6478047
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
            .addConverterFactory(ToStringConverterFactory())
//        }

        val retrofit = retrofitBuilder.build()
        return retrofit.create(RetrofitService::class.java)

    }


     fun getInterceptorClient(): OkHttpClient {

        //setting request timeout
        val okHttpClientBuilder = OkHttpClient()
            .newBuilder()
            .addInterceptor(NetworkConnectionInterceptor())
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)


        if (BuildConfig.DEBUG) {
            //this will log all the request details in logcat so only use it in debug builds
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        setHeaders(okHttpClientBuilder)

        return okHttpClientBuilder.build()
    }

    /**
     * This will set headers to all retrofit requests
     */

    private fun setHeaders(
        okHttpClientBuilder: OkHttpClient.Builder
    ): OkHttpClient.Builder {

        //Headers to be included in all requests
        okHttpClientBuilder.addInterceptor { chain ->

            val accessToken = PreferencesUtils.getAccessToken(AuthTestApplication.getContext())

            val request = chain.request()

            val url = request.url()
                .newBuilder()
//                .addQueryParameter("global query", global query params if required)
                .build()

            val requestBuilder = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url)

            if (!accessToken.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $accessToken")
            }

            chain.proceed(requestBuilder.build())
        }
        return okHttpClientBuilder
    }

    fun getBasicHeader(userName: String, password: String): String {
        val header =
            "Basic ${Base64.encodeToString(("$userName:$password").toByteArray(), Base64.NO_WRAP)}"
        return header
    }
}