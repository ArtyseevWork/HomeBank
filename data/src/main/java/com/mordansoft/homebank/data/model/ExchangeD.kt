package com.mordansoft.homebank.data.model

data class ExchangeD(
    val currencyCodeA: Int,
    val currencyCodeB: Int,
    val date: Long,
    val rateBuy: Float,
    val rateCross: Float,
    val rateSell: Float,
)