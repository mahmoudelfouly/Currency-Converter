package com.melfouly.currency.repository

import android.database.Cursor
import android.util.Log
import com.melfouly.currency.local.Currencies
import com.melfouly.currency.local.CurrenciesDao
import com.melfouly.currency.local.DbHelper
import com.melfouly.currency.local.Transition
import com.melfouly.currency.network.RetrofitService
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(
    private val retrofitService: RetrofitService,
    private val currenciesDao: CurrenciesDao,
    private val dbHelper: DbHelper
) : Repository {

    init {
        Log.d("Instance", "RepositoryImpl RetrofitService object: $retrofitService")
        Log.d("Instance", "RepositoryImpl CurrentDao object: $currenciesDao")
        Log.d("Instance", "RepositoryImpl DbHelper object: $dbHelper")
    }

    override fun getAllCurrencies() = retrofitService.getAllCurrencies()
    override fun convertCurrency(from: String, to: String, amount: Double) =
        retrofitService.convertCurrency(from, to, amount)

    override fun saveCurrencies(currencies: Currencies) = currenciesDao.insertCurrencies(currencies)
    override fun getCurrenciesLocally() = currenciesDao.getAllCurrencies()

    override fun currencyExistsBySymbol(symbol: String): Boolean =
        currenciesDao.currencyExistsBySymbol(symbol)

    override fun saveTransition(transition: Transition) = dbHelper.insertTransition(transition)

    override fun getAllTransitions(): Observable<MutableList<Transition>> = dbHelper.getAllTransitions()
}