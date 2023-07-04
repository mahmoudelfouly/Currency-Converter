package com.melfouly.currency.repository

import android.database.Cursor
import com.melfouly.currency.local.Currencies
import com.melfouly.currency.local.Transition
import com.melfouly.currency.model.Conversion
import com.melfouly.currency.model.CurrencyList
import io.reactivex.rxjava3.core.Observable

interface Repository {

    fun getAllCurrencies(): Observable<CurrencyList>
    fun convertCurrency(from: String, to: String, amount: Double): Observable<Conversion>
    fun saveCurrencies(currencies: Currencies)
    fun getCurrenciesLocally(): Observable<List<String>>

    fun currencyExistsBySymbol(symbol: String): Boolean

    fun saveTransition(transition: Transition)

    fun getAllTransitions(): Observable<MutableList<Transition>>
}