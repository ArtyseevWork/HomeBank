package com.mordansoft.homebank.data.repo

import com.mordansoft.homebank.data.model.ExchangeD
import com.mordansoft.homebank.data.model.ExchangesD
import com.mordansoft.homebank.data.storage.exchange_rates.RetrofitInstance
import com.mordansoft.homebank.domain.model.Currency
import com.mordansoft.homebank.domain.model.Exchange
import com.mordansoft.homebank.domain.repo.ExchangeRepo
import retrofit2.Response

class ExchangeRepoImpl(): ExchangeRepo {

    override suspend fun getAllExchanges(mainCurrency : Int?): List<Exchange> {
        var resultlist = ArrayList<Exchange>()
        var response : Response<ExchangesD>  = RetrofitInstance.api.getExchanges()
        if(response.isSuccessful && response.body() != null) {
            var exchangesD : ExchangesD = response.body()!!
            resultlist = exchangeDToExchangeList(exchangesD, mainCurrency)
        } else {
            println(response.errorBody());
        }
        return  resultlist
    }

    private fun exchangeDToExchange(exchangeD: ExchangeD): Exchange {
        var rateCross = exchangeD.rateCross
        if (rateCross == 0F){
            rateCross = (exchangeD.rateBuy + exchangeD.rateSell) /2
        }
        return Exchange(
                currencyCodeA = exchangeD.currencyCodeA,
                currencyCodeB = exchangeD.currencyCodeB,
                rate          = rateCross
        )
    }

    private fun exchangeDToExchangeList(list: ExchangesD, mainCurrency : Int?): ArrayList<Exchange> {
        var resultlist = ArrayList<Exchange>()
        for (e in list){
            if (mainCurrency != null
                && e.currencyCodeB == mainCurrency
                && Currency.map.containsKey(e.currencyCodeA)
                ){
                    resultlist.add(exchangeDToExchange(e))
            } else if (   Currency.map.containsKey(e.currencyCodeA)
                && Currency.map.containsKey(e.currencyCodeB)){
                resultlist.add(exchangeDToExchange(e))
            }
        }
       return resultlist
    }
}