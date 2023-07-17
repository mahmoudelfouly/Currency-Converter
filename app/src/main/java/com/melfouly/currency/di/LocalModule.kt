package com.melfouly.currency.di

import android.app.Application
import androidx.room.Room
import com.melfouly.data.local.CurrenciesDao
import com.melfouly.data.local.CurrenciesDatabase
import com.melfouly.data.local.DbHelper
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

    @Singleton
    @Provides
    fun provideSQLDatabase(application: Application): DbHelper {
        return DbHelper(application, null)
    }
}