package com.melfouly.currency.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.melfouly.currency.local.CurrenciesDao
import com.melfouly.currency.local.CurrenciesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): CurrenciesDao {
        return Room.databaseBuilder(
            application.applicationContext,
            CurrenciesDatabase::class.java,
            "currenciesDb.db"
        ).build().currenciesDao()
    }
}