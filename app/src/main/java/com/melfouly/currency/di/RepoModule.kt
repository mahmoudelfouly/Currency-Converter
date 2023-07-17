package com.melfouly.currency.di

import com.melfouly.data.local.CurrenciesDao
import com.melfouly.data.local.DbHelper
import com.melfouly.data.network.RetrofitService
import com.melfouly.data.repository.RepositoryImpl
import com.melfouly.domain.repository.Repository
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
    fun provideRepository(
        retrofitService: RetrofitService,
        currenciesDao: CurrenciesDao,
        dbHelper: DbHelper
    ): Repository {
        return RepositoryImpl(retrofitService, currenciesDao, dbHelper)
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
