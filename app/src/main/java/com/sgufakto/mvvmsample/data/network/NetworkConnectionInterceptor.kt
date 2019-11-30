package com.sgufakto.mvvmsample.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.sgufakto.mvvmsample.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
): Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if( !isInternertAvailable() )
            throw NoInternetException("Make sure you have an active data connection")

        return chain.proceed(chain.request())

    }

    private fun isInternertAvailable(): Boolean {

        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }

    }

}