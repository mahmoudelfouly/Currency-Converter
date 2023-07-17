package com.melfouly.domain.model

data class Transition(
    val fromAmount: Double,
    val fromSymbol: String,
    val toAmount: Double,
    val toSymbol: String
)