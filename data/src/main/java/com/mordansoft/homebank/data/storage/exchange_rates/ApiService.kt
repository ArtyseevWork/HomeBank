package com.mordansoft.homebank.data.storage.exchange_rates

import com.mordansoft.homebank.data.model.ExchangesD
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("bank/currency")
    suspend fun  getExchanges(): Response<ExchangesD>
}