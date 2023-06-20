package com.melfouly.currency.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

class MainInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Adding access key in url for every request or response.
        val url = chain.request().url().newBuilder()
            .addQueryParameter("apikey", "enter your api key here").build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Platform", "Android")
            .build()

        return chain.proceed(request)
    }
}