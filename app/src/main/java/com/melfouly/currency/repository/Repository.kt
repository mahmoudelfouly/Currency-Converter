package com.melfouly.currency.repository

import com.melfouly.currency.local.Currencies
import com.melfouly.currency.model.Conversion
import com.melfouly.currency.model.CurrencyList
import io.reactivex.rxjava3.core.Observable

interface Repository {

    fun getAllCurrencies(): Observable<CurrencyList>
    fun convertCurrency(from: String, to: String, amount: Double): Observable<Conversion>
    fun saveCurrencies(currencies: Currencies)
    fun getCurrenciesLocally(): Observable<List<String>>
}