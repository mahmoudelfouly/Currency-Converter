package com.melfouly.domain.repository

import com.melfouly.domain.model.Conversion
import com.melfouly.domain.model.Currencies
import com.melfouly.domain.model.CurrencyList
import com.melfouly.domain.model.Transition
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