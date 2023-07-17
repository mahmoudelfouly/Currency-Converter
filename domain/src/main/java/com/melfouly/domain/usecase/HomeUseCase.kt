package com.melfouly.domain.usecase

import com.melfouly.domain.model.Currencies
import com.melfouly.domain.model.Transition
import com.melfouly.domain.repository.Repository

class HomeUseCase(private val repositoryImpl: Repository) {
    fun getAllCurrencies() = repositoryImpl.getAllCurrencies()

    fun convertCurrency(from: String, to: String, amount: Double) =
        repositoryImpl.convertCurrency(from, to, amount)

    fun saveCurrencies(currencies: Currencies) = repositoryImpl.saveCurrencies(currencies)

    fun getCurrenciesLocally() = repositoryImpl.getCurrenciesLocally()

    fun currencyExistsBySymbol(symbol: String) = repositoryImpl.currencyExistsBySymbol(symbol)

    fun saveTransition(transition: Transition) = repositoryImpl.saveTransition(transition)
}