package com.melfouly.domain.model

data class Conversion(
    val success: Boolean,
    val result: Double,
    val error: ResponseError?
)