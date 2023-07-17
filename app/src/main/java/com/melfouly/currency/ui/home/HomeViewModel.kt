package com.melfouly.currency.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melfouly.domain.model.Conversion
import com.melfouly.domain.model.Currencies
import com.melfouly.domain.model.CurrencyList
import com.melfouly.domain.model.Transition
import com.melfouly.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase) :
    ViewModel() {

    private val TAG = "HomeViewModel"
    private val _currencyList = MutableLiveData<CurrencyList>()
    val currencyList: LiveData<CurrencyList> get() = _currencyList
    private val _localCurrencies = MutableLiveData<List<String>>()
    val localCurrencies: LiveData<List<String>> get() = _localCurrencies
    private val _convertedValue = MutableLiveData<Conversion>()
    val convertedValue: LiveData<Conversion> get() = _convertedValue
    lateinit var currenciesDisposable: Disposable
    lateinit var localCurrenciesDisposable: Disposable
    lateinit var converterDisposable: Disposable

    init {
        Log.d("Instance", "HomeViewModel Repository object: $useCase")
    }

    fun getAllCurrencies() {
        val currenciesObservable = useCase.getAllCurrencies() // Creating Observable
        currenciesObservable.subscribeOn(Schedulers.io()) // UpStream in IO thread
            .observeOn(Schedulers.io()) // DownStream in IO thread
            .doAfterNext {
                it.symbols.forEach { (key, value) ->
                    if (!useCase.currencyExistsBySymbol(key)) {
                        val currency = Currencies(symbol = key, name = value)
                        Log.d(
                            TAG,
                            "getAllCurrencies: doAfterNext called, currencyExistsBySymbol is false for symbol $key"
                        )
                        useCase.saveCurrencies(currency)
                    }
                    Log.d(
                        TAG,
                        "getAllCurrencies: doAfterNext called, currencyExistsBySymbol is true"
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread()) // DownStream in Main thread
            .subscribe(setAllCurrenciesObserver()) // Create the subscription
    }

    fun convertCurrency(from: String, to: String, amount: Double) {
        val converterObservable =
            useCase.convertCurrency(from, to, amount) // Creating Observable
        converterObservable.subscribeOn(Schedulers.io()) // Upstream in IO thread
            .doAfterNext {
                val transition = Transition(amount, from, it.result, to)
                Log.d(TAG, "convertCurrency: doAfterNext called, transition from $from to $to")
                useCase.saveTransition(transition)
            }
            .observeOn(AndroidSchedulers.mainThread()) // Downstream in Main thread
            .subscribe(setConvertCurrencyObserver()) // Create the subscription
    }

    fun getCurrenciesLocally() {
        val localCurrencies = useCase.getCurrenciesLocally() // Creating Observable
        localCurrencies.subscribeOn(Schedulers.io()) // Upstream in IO thread
            .observeOn(AndroidSchedulers.mainThread()) // Downstream in Main thread
            .subscribe(setLocalCurrenciesObserver()) // Create the subscription
    }

    // Create getAllCurrencies Observer
    private fun setAllCurrenciesObserver(): Observer<CurrencyList> {
        return object : Observer<CurrencyList> {
            override fun onSubscribe(d: Disposable) {
                currenciesDisposable = d
            }

            override fun onNext(t: CurrencyList) {
                Log.d(TAG, "currenciesObserver onNext: ${t.success}")
                Log.d(TAG, "currenciesObserver onNext: ${t.error?.code}")
                Log.d(TAG, "currenciesObserver onNext: ${t.error?.info}")
                _currencyList.value = t
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "currenciesObserver onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "currenciesObserver onComplete: called")
            }
        }
    }

    // Create convertCurrency Observer
    private fun setConvertCurrencyObserver(): Observer<Conversion> {
        return object : Observer<Conversion> {
            override fun onSubscribe(d: Disposable) {
                converterDisposable = d
            }

            override fun onNext(t: Conversion) {
                Log.d(TAG, "converterObserver onNext: ${t.success}")
                Log.d(TAG, "converterObserver onNext: ${t.result}")
                Log.d(TAG, "converterObserver onNext: ${t.error?.code}")
                Log.d(TAG, "converterObserver onNext: ${t.error?.info}")
                _convertedValue.value = t
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "converterObserver onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "converterObserver onComplete: called")
            }
        }
    }

    private fun setLocalCurrenciesObserver(): Observer<List<String>> {
        return object : Observer<List<String>> {
            override fun onSubscribe(d: Disposable) {
                localCurrenciesDisposable = d
            }

            override fun onNext(t: List<String>) {
                _localCurrencies.value = t.sorted()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "localCurrenciesObserver onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "localCurrenciesObserver onComplete: called")
            }
        }
    }


    override fun onCleared() {
        converterDisposable.dispose()
        currenciesDisposable.dispose()
        localCurrenciesDisposable.dispose()
        super.onCleared()
    }

}