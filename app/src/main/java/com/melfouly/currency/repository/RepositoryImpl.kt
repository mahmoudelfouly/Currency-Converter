package com.melfouly.currency.repository

import android.util.Log
import com.melfouly.currency.local.Currencies
import com.melfouly.currency.local.CurrenciesDao
import com.melfouly.currency.network.RetrofitService

class RepositoryImpl (
    private val retrofitService: RetrofitService,
    private val currenciesDao: CurrenciesDao
): Repository {

    init {
        Log.d("Instance", "RepositoryImpl RetrofitService object: $retrofitService")
        Log.d("Instance", "RepositoryImpl CurrentDao object: $currenciesDao")

    }

    override fun getAllCurrencies() = retrofitService.getAllCurrencies()
    override fun convertCurrency(from: String, to: String, amount: Double) =
        retrofitService.convertCurrency(from, to, amount)

    override fun saveCurrencies(currencies: Currencies) = currenciesDao.insertCurrencies(currencies)
    override fun getCurrenciesLocally() = currenciesDao.getAllCurrencies()

}