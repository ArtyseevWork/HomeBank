package com.mordansoft.homebank.ui.profit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.domain.usecase.profit.*

class ProfitViewModelFactory(private val getProfitByIdUc       : GetProfitByIdUc,
                             private val setProfitUc           : SetProfitUc,
                             private val deleteProfitUc        : DeleteProfitUc
) :  ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfitViewModel(
                getProfitByIdUc        = getProfitByIdUc       ,
                setProfitUc            = setProfitUc           ,
                deleteProfitUc         = deleteProfitUc
            ) as T
        }
}