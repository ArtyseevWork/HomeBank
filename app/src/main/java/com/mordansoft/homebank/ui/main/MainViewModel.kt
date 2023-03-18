package com.mordansoft.homebank.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.PeriodAccounting
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.period.GetPeriodAccountingUc
import com.mordansoft.homebank.domain.usecase.purchase.GetMainPurchasesUc
import kotlinx.coroutines.launch


class MainViewModel(private val getMainPurchasesUc      : GetMainPurchasesUc,
                    private val getPeriodAccountingUc   : GetPeriodAccountingUc) : ViewModel() {

    private val _purchases = MutableLiveData<ArrayList<Purchase>>()
    var purchases: LiveData<ArrayList<Purchase>> = _purchases

    private val _accounting = MutableLiveData<PeriodAccounting>()
    var accounting: LiveData<PeriodAccounting> = _accounting


    fun getPurchases(){
        viewModelScope.launch {
            _purchases.value = getMainPurchasesUc.execute()
        }
    }

    fun getAccounting(periodId : Int){ // todo delete arguments
        viewModelScope.launch {
            _accounting.value = getPeriodAccountingUc.execute(periodId)
        }
    }

}

