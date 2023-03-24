package com.mordansoft.homebank.domain.usecase.exchange_rates

import com.mordansoft.homebank.domain.model.Currency
import com.mordansoft.homebank.domain.model.Exchange
import com.mordansoft.homebank.domain.repo.ExchangeRepo
import com.mordansoft.homebank.domain.repo.PreferencesRepo

class GetExchangeRatesUc (private val exchangeRepo: ExchangeRepo,
                          private val preferencesRepo: PreferencesRepo) {

    suspend fun execute(): ArrayList<Currency> {
        val currencyList : ArrayList<Currency> = ArrayList<Currency>()
        val preferences = preferencesRepo.getPreferences()
        val exchangeList: List<Exchange> = exchangeRepo.getAllExchanges(preferences.mainCurrencyId)
        if (exchangeList.isNotEmpty()){
            for (e in exchangeList) {
                val currencyCodeA: Int = e.currencyCodeA
                val currencyNameA: String? = Currency.map[e.currencyCodeA]
                var currencyCountA: Float = 1F
                val currencyCodeB: Int = e.currencyCodeB
                val currencyNameB: String? = Currency.map[e.currencyCodeB]
                var currencyCountB: Float = 1F
                if (e.rate > 1){
                    currencyCountB = e.rate
                } else {
                    currencyCountA = 1/e.rate
                }
                if (currencyNameA != null && currencyNameB != null){
                    currencyList.add(Currency(currencyCodeA = currencyCodeA ,
                        currencyNameA = currencyNameA ,
                        currencyCountA= currencyCountA,
                        currencyCodeB = currencyCodeB ,
                        currencyNameB = currencyNameB ,
                        currencyCountB= currencyCountB,))
                }
            }
        }
        return currencyList
    }
}