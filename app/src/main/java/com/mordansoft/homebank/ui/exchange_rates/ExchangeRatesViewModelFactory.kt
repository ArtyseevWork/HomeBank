package com.mordansoft.homebank.ui.exchange_rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.domain.usecase.exchange_rates.GetExchangeRatesUc

class ExchangeRatesViewModelFactory(private val getExchangeRatesUc : GetExchangeRatesUc)
    :  ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExchangeRatesViewModel(
            getExchangeRatesUc = getExchangeRatesUc
        ) as T
    }
}