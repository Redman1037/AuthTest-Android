package rest.restutils

import com.appstyx.authtest.common.isNetworkAvailable
import com.appstyx.authtest.ui.AuthTestApplication
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor : Interceptor {

    val applicationContext=AuthTestApplication.getContext()

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable(applicationContext))
            throw NoInternetException("Make sure you have an active data connection")
        return chain.proceed(chain.request())
    }
}