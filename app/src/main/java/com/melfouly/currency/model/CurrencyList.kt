package com.melfouly.currency.model

data class CurrencyList(
    val success: Boolean,
    val symbols: HashMap<String, String>,
    val error: ResponseError?
    )

data class ResponseError(
    val code: Int,
    val info: String
)
