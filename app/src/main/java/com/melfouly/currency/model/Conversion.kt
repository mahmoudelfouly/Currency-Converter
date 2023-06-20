package com.melfouly.currency.model

data class Conversion(
    val success: Boolean,
    //val query: QueryResponse,
    //val info: InfoResponse,
    //val historical: Boolean,
    //val date: String,
    val result: Double,
    val error: ResponseError?
)

data class QueryResponse(
    val from: String,
    val to: String,
    val amount: Double
)

data class InfoResponse(
    val timestamp: Long,
    val rate: Double
)