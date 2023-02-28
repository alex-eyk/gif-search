package com.alex.eyk.gifsearch.di

import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.GifRepository
import com.alex.eyk.gifsearch.data.repo.impl.RemoteGifRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideRemoteGifRepository(
        gifService: GifService
    ): GifRepository {
        return RemoteGifRepository(gifService)
    }
}
