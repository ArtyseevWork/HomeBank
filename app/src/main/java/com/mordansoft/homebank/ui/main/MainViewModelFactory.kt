package com.mordansoft.homebank.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.domain.usecase.period.GetPeriodAccountingUc

import com.mordansoft.homebank.domain.usecase.purchase.GetMainPurchasesUc



class MainViewModelFactory(val getMainPurchasesUc   : GetMainPurchasesUc,
                           val getPeriodAccountingUc: GetPeriodAccountingUc)
    :  ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getMainPurchasesUc    = getMainPurchasesUc,
            getPeriodAccountingUc = getPeriodAccountingUc
        ) as T
    }
}