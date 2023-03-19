package com.mordansoft.homebank.ui.profit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.profit.*

class ProfitViewModelFactory(private val getProfitByIdUc : GetProfitByIdUc,
                             private val setProfitUc     : SetProfitUc,
                             private val deleteProfitUc  : DeleteProfitUc,
                             private val getPeriodUc     : GetPeriodUc
) :  ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfitViewModel(
                getProfitByIdUc = getProfitByIdUc,
                setProfitUc     = setProfitUc    ,
                deleteProfitUc  = deleteProfitUc ,
                getPeriodUc     = getPeriodUc
            ) as T
        }
}