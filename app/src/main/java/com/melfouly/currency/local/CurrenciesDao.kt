package com.melfouly.currency.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.melfouly.currency.model.CurrencyList
import io.reactivex.rxjava3.core.Observable

@Dao
interface CurrenciesDao {
    @Query("SELECT symbol FROM currencies ORDER BY symbol ASC")
    fun getAllCurrencies(): Observable<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(currency: Currencies)
}