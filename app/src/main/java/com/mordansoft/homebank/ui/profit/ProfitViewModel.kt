package com.mordansoft.homebank.ui.profit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.profit.*
import kotlinx.coroutines.launch

class ProfitViewModel(private val getProfitByIdUc       : GetProfitByIdUc,
                      private val setProfitUc           : SetProfitUc,
                      private val deleteProfitUc        : DeleteProfitUc,
                      private val getPeriodUc           : GetPeriodUc
) : ViewModel() {


    private val _profit = MutableLiveData<Profit>()
    var profit: LiveData<Profit> = _profit

    private val _period = MutableLiveData<Period>()
    var period: LiveData<Period> = _period



    fun getProfit(profitId : Long){
        viewModelScope.launch {
            _profit.value = getProfitByIdUc.execute(profitId = profitId)
        }
    }

    fun setProfit(profit : Profit){
        viewModelScope.launch {
            setProfitUc.execute(profit = profit)
        }
    }

    fun deleteProfit(profit : Profit){
        viewModelScope.launch {
            deleteProfitUc.execute(profit = profit)
        }
    }

    fun getPeriod(periodId : Int?){
        viewModelScope.launch {
            _period.value      = getPeriodUc.execute(periodId)
        }
    }
}

