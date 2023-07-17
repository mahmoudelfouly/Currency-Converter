package com.melfouly.currency.ui.transitions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melfouly.domain.model.Transition
import com.melfouly.domain.usecase.TransitionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TransitionsViewModel @Inject constructor(private val useCase: TransitionsUseCase) :
    ViewModel() {

    private val TAG = "TransitionsViewModel"
    private val _transitionList = MutableLiveData<MutableList<Transition>>()
    val transitionList: LiveData<MutableList<Transition>> get() = _transitionList
    lateinit var transitionsDisposable: Disposable

    init {
        Log.d("Instance", "TransitionsViewModel Repository object: $useCase")
    }

    fun getAllTransitions() {
        val transitionsObservable = useCase.getAllTransitions()
        transitionsObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(setAllTransition())
    }

    private fun setAllTransition(): Observer<MutableList<Transition>> {
        return object : Observer<MutableList<Transition>> {
            override fun onSubscribe(d: Disposable) {
                transitionsDisposable = d
            }

            override fun onNext(t: MutableList<Transition>) {
                Log.d(TAG, "transitionsObserver onNext: ${t[0]}")
                _transitionList.value = t
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "transitionsObserver onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "transitionsObserver onComplete: called")
            }
        }
    }

    override fun onCleared() {
        transitionsDisposable.dispose()
        super.onCleared()
    }

}