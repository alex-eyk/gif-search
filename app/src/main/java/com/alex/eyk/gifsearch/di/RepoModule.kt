package com.alex.eyk.gifsearch.di

import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.GifRepository
import com.alex.eyk.gifsearch.data.repo.SuggestionsRepository
import com.alex.eyk.gifsearch.data.repo.impl.GifRemoteRepository
import com.alex.eyk.gifsearch.data.repo.impl.SuggestionsRemoteRepository
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
    fun provideGifRemoteRepository(
        gifService: GifService
    ): GifRepository {
        return GifRemoteRepository(gifService)
    }

    @Singleton
    @Provides
    fun provideSuggestionsRemoteRepository(
        gifService: GifService
    ): SuggestionsRepository {
        return SuggestionsRemoteRepository(gifService)
    }
}
