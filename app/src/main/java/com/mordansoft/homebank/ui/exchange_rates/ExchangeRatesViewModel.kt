package com.mordansoft.homebank.ui.exchange_rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Currency
import com.mordansoft.homebank.domain.usecase.exchange_rates.GetExchangeRatesUc
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(private val getExchangeRatesUc : GetExchangeRatesUc): ViewModel() {


    private val _myCurrencyList: MutableLiveData<ArrayList<Currency>>
                               = MutableLiveData<ArrayList<Currency>>()
    val myCurrencyList: LiveData<ArrayList<Currency>> = _myCurrencyList

    fun getCurrencyList(){
        viewModelScope.launch {
            _myCurrencyList.value = getExchangeRatesUc.execute()
        }
    }

}