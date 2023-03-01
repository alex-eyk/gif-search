package com.alex.eyk.gifsearch.data.net.interceptor

import com.alex.eyk.gifsearch.data.domain.NetworkState
import com.alex.eyk.gifsearch.data.domain.NetworkStateUseCase
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class GiphyCacheInterceptor(
    private val networkStateUseCase: NetworkStateUseCase
) : Interceptor {

    companion object {

        private const val ONLINE_MAX_CACHE_AGE = 30

        private const val OFFLINE_MAX_CACHE_AGE = 60 * 60 * 24 * 7
    }

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
                        "public, max-age=$ONLINE_MAX_CACHE_AGE"
                    )
                    .build()
            }
            is NetworkState.OFFLINE -> {
                newRequest = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=$OFFLINE_MAX_CACHE_AGE"
                    )
                    .build()
            }
        }
        return chain.proceed(newRequest)
    }
}
