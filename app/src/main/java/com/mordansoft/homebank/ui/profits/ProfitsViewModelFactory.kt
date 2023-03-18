package com.mordansoft.homebank.ui.profits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.mordansoft.homebank.domain.usecase.profit.GetMainProfitsUc
import com.mordansoft.homebank.domain.usecase.profit.GetProfitsAccountingUc


class ProfitsViewModelFactory(val getMainProfitsUc       : GetMainProfitsUc,
                              val getProfitsAccountingUc : GetProfitsAccountingUc)
    :  ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfitsViewModel(
            getMainProfitsUc       = getMainProfitsUc,
            getProfitsAccountingUc = getProfitsAccountingUc
        ) as T
    }
}