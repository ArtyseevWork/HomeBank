package com.mordansoft.homebank.ui.profit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.usecase.profit.*
import kotlinx.coroutines.launch

class ProfitViewModel(private val getProfitByIdUc       : GetProfitByIdUc,
                      private val setProfitUc           : SetProfitUc,
                      private val deleteProfitUc        : DeleteProfitUc
) : ViewModel() {


    private val _profit = MutableLiveData<Profit>()
    var profit: LiveData<Profit> = _profit


    fun getProfit(profitId : Long){
        viewModelScope.launch {
            _profit.value = getProfitByIdUc.execute(profitId = profitId)
        }
    }

    fun setProfit(profit : Profit){
        viewModelScope.launch {
            setProfit(profit = profit)
            _profit.value = profit
        }
    }
}

