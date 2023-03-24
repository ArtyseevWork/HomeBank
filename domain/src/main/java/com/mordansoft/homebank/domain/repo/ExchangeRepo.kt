package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Exchange

interface ExchangeRepo {
    suspend fun getAllExchanges(mainCurrency : Int?): List<Exchange>
}