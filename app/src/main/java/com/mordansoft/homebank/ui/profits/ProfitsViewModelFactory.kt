package com.mordansoft.homebank.ui.profits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.mordansoft.homebank.domain.usecase.profit.GetMainProfitsUc



class ProfitsViewModelFactory(val getMainProfitsUc: GetMainProfitsUc):  ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfitsViewModel(
            getMainProfitsUc = getMainProfitsUc
        ) as T
    }
}