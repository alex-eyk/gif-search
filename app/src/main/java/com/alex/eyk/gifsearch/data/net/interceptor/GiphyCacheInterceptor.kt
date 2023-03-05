package com.alex.eyk.gifsearch.data.net.interceptor

import com.alex.eyk.gifsearch.domain.net.NetworkState
import com.alex.eyk.gifsearch.domain.net.NetworkStateUseCase
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class GiphyCacheInterceptor(
    private val networkStateUseCase: NetworkStateUseCase,
    private val onlineMaxCacheAge: Int,
    private val offlineMaxCacheAge: Int
) : Interceptor {

    override fun intercept(
        chain: Chain
    ): Response {
        val request = chain.request()
        val newRequest: Request
        when (networkStateUseCase.getState()) {
            is NetworkState.ONLINE -> {
                newRequest = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, max-age=$onlineMaxCacheAge"
                    )
                    .build()
            }
            is NetworkState.OFFLINE -> {
                newRequest = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=$offlineMaxCacheAge"
                    )
                    .build()
            }
        }
        return chain.proceed(newRequest)
    }
}
