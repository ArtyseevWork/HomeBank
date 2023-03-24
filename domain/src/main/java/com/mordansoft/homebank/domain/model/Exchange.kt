package com.mordansoft.homebank.domain.model

data class Exchange(
    val currencyCodeA: Int,
    val currencyCodeB: Int,
    val rate: Float,
)