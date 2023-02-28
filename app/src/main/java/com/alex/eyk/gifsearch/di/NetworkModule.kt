package com.alex.eyk.gifsearch.di

import com.alex.eyk.gifsearch.data.net.interceptor.GiphyApiKeyInterceptor
import com.alex.eyk.gifsearch.data.net.service.GifService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
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
    fun provideGifService(): GifService {
        return Retrofit.Builder()
            .baseUrl(GIF_API_SERVER_URL)
            .client(provideGiphyClient())
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(GifService::class.java)
    }

    private fun provideGiphyClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = BODY
                }
            )
            .addInterceptor(
                GiphyApiKeyInterceptor()
            )
            .build()
    }
}
