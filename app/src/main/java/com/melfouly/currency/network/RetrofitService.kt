package com.melfouly.currency.network

import com.melfouly.currency.model.Conversion
import com.melfouly.currency.model.CurrencyList
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("symbols")
    fun getAllCurrencies(): Observable<CurrencyList>

    @GET("convert")
    fun convertCurrency(@Query("from") from: String, @Query("to") to: String, @Query("amount") amount: Double): Observable<Conversion>
}
