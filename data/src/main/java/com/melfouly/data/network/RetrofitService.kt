package com.melfouly.data.network

import com.melfouly.domain.model.Conversion
import com.melfouly.domain.model.CurrencyList
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("symbols")
    fun getAllCurrencies(): Observable<CurrencyList>

    @GET("convert")
    fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Observable<Conversion>
}
