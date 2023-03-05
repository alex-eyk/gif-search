package com.alex.eyk.gifsearch.di

import android.content.Context
import com.alex.eyk.gifsearch.domain.clipboard.CopyUseCase
import com.alex.eyk.gifsearch.domain.clipboard.CopyUseCaseImpl
import com.alex.eyk.gifsearch.domain.net.NetworkStateUseCase
import com.alex.eyk.gifsearch.domain.net.NetworkStateUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideNetworkStateUseCase(
        @ApplicationContext appContext: Context
    ): NetworkStateUseCase {
        return NetworkStateUseCaseImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideCopyUseCase(
        @ApplicationContext appContext: Context
    ): CopyUseCase {
        return CopyUseCaseImpl(appContext)
    }
}
