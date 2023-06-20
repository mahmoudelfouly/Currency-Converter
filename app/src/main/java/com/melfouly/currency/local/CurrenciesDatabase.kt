package com.melfouly.currency.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Currencies::class], version = 1, exportSchema = false)
abstract class CurrenciesDatabase: RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao
}
