package com.melfouly.domain.model

data class CurrencyList(
    val success: Boolean,
    val symbols: HashMap<String, String>,
    val error: ResponseError?
)
