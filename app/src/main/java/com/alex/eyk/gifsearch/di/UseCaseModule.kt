package com.alex.eyk.gifsearch.di

import android.content.Context
import com.alex.eyk.gifsearch.data.domain.NetworkStateUseCase
import com.alex.eyk.gifsearch.data.domain.NetworkStateUseCaseImpl
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
}
