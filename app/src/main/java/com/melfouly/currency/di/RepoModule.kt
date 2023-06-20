package com.melfouly.currency.di

import com.melfouly.currency.local.CurrenciesDao
import com.melfouly.currency.network.RetrofitService
import com.melfouly.currency.repository.Repository
import com.melfouly.currency.repository.RepositoryImpl
import dagger.Binds
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
    fun provideRepository(retrofitService: RetrofitService, currenciesDao: CurrenciesDao): Repository {
        return RepositoryImpl(retrofitService, currenciesDao)
    }
}

/**
    * Another way for injecting (Abstract classes/Interfaces)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    }

   * then use @Inject constructor in RepositoryImpl class
 */
