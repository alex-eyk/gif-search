package com.alex.eyk.gifsearch.data.net.interceptor

import com.alex.eyk.gifsearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class GiphyApiKeyInterceptor : Interceptor {

    override fun intercept(
        chain: Chain
    ): Response {
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter(
                "api_key",
                BuildConfig.GIPHY_API_KEY
            )
            .build()
        val request = original.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}
