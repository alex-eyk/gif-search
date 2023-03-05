package com.alex.eyk.gifsearch.di

import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.SuggestionsRepository
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
    fun provideSuggestionsRemoteRepository(
        gifService: GifService
    ): SuggestionsRepository {
        return SuggestionsRemoteRepository(gifService)
    }
}
