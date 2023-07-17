package com.melfouly.currency.di

import com.melfouly.domain.repository.Repository
import com.melfouly.domain.usecase.HomeUseCase
import com.melfouly.domain.usecase.TransitionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideHomeUseCase(repositoryImpl: Repository): HomeUseCase {
        return HomeUseCase(repositoryImpl)
    }

    @Singleton
    @Provides
    fun provideTransitionsUseCase(repositoryImpl: Repository): TransitionsUseCase {
        return TransitionsUseCase(repositoryImpl)
    }
}