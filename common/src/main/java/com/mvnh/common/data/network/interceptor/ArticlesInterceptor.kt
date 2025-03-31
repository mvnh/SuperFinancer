package com.mvnh.common.data.network.interceptor

import com.mvnh.common.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ArticlesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api-key", BuildConfig.NYTIMES_API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
