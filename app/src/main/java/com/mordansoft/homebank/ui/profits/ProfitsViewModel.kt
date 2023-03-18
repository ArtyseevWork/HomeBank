package com.mordansoft.homebank.ui.profits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.ProfitsAccounting
import com.mordansoft.homebank.domain.usecase.profit.GetMainProfitsUc
import com.mordansoft.homebank.domain.usecase.profit.GetProfitsAccountingUc
import kotlinx.coroutines.launch


class ProfitsViewModel(private val getMainProfitsUc : GetMainProfitsUc,
                       private val getProfitsAccountingUc: GetProfitsAccountingUc) : ViewModel() {

    private val _profits = MutableLiveData<ArrayList<Profit>>()
    var profits: LiveData<ArrayList<Profit>> = _profits

    private val _accounting = MutableLiveData<ProfitsAccounting>()
    var accounting: LiveData<ProfitsAccounting> = _accounting


    fun getProfits(){
        viewModelScope.launch {
            _profits.value = getMainProfitsUc.execute()
        }
    }

    fun getAccounting(periodId : Int){  // todo delete arguments
        viewModelScope.launch {
            _accounting.value = getProfitsAccountingUc.execute(periodId = periodId)
        }
    }

}

