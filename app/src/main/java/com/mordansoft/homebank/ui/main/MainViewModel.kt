package com.mordansoft.homebank.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.PeriodAccounting
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.period.GetPeriodAccountingUc
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.purchase.GetMainPurchasesUc
import kotlinx.coroutines.launch


class MainViewModel(private val getMainPurchasesUc      : GetMainPurchasesUc,
                    private val getPeriodAccountingUc   : GetPeriodAccountingUc,
                    private val getPeriodUc             : GetPeriodUc
) : ViewModel() {

    private val _period = MutableLiveData<Period>()
    var period: LiveData<Period> = _period

    private val _purchases = MutableLiveData<ArrayList<Purchase>>()
    var purchases: LiveData<ArrayList<Purchase>> = _purchases

    private val _accounting = MutableLiveData<PeriodAccounting>()
    var accounting: LiveData<PeriodAccounting> = _accounting


    fun getPurchases(periodId : Int, statusId : Int){
        viewModelScope.launch {
            _purchases.value = getMainPurchasesUc.execute(periodId, statusId)
        }
    }

    fun getPeriodsData(periodId : Int?){
        viewModelScope.launch {
            val newPeriod : Period = getPeriodUc.execute(periodId)
            _period.value      = newPeriod
            _accounting.value  = getPeriodAccountingUc.execute(newPeriod.id) //todo async
            _purchases.value   = getMainPurchasesUc.execute(newPeriod.id)
        }
    }

}

