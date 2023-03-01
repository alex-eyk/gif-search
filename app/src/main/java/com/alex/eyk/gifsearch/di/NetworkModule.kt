package com.alex.eyk.gifsearch.di

import android.content.Context
import com.alex.eyk.gifsearch.data.domain.NetworkStateUseCase
import com.alex.eyk.gifsearch.data.net.interceptor.GiphyApiKeyInterceptor
import com.alex.eyk.gifsearch.data.net.interceptor.GiphyCacheInterceptor
import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.pref.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val GIF_API_SERVER_URL = "https://api.giphy.com/v1/gifs/"

    @Singleton
    @Provides
    fun provideGifService(
        giphyHttpClient: OkHttpClient
    ): GifService {
        return Retrofit.Builder()
            .baseUrl(GIF_API_SERVER_URL)
            .client(giphyHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(GifService::class.java)
    }

    @Singleton
    @Provides
    fun provideGiphyClient(
        cache: Cache,
        apiKeyInterceptor: GiphyApiKeyInterceptor,
        cacheInterceptor: GiphyCacheInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = BODY
                }
            )
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(cacheInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGiphyApiKeyInterceptor(): GiphyApiKeyInterceptor {
        return GiphyApiKeyInterceptor()
    }

    @Singleton
    @Provides
    fun provideGiphyCacheInterceptor(
        networkStateUseCase: NetworkStateUseCase
    ): GiphyCacheInterceptor {
        return GiphyCacheInterceptor(networkStateUseCase)
    }

    @Singleton
    @Provides
    fun provideCache(
        @ApplicationContext appContext: Context,
        prefs: AppPreferences
    ): Cache {
        return Cache(
            appContext.cacheDir,
            calculateCacheSizeInB(prefs.getMaxCacheSizeInMB())
        )
    }

    private fun calculateCacheSizeInB(
        maxSizeInMB: Int
    ): Long {
        return (maxSizeInMB * 1024L * 1024L * AppPreferences.CACHE_FOR_RESPONSES).toLong()
    }
}
