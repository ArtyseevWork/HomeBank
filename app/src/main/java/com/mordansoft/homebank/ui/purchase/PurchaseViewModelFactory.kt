package com.mordansoft.homebank.ui.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.domain.usecase.*
import com.mordansoft.homebank.ui.main.MainViewModel

class PurchaseViewModelFactory(private val getPurchaseByIdUc       : GetPurchaseByIdUc,
                               private val getDaughterPurchasesUc  : GetDaughterPurchasesUc,
                               private val getSumDaughterPurchaseUc: GetSumDaughterPurchaseUc,
                               private val setPurchaseUc           : SetPurchaseUc,
                               private val deletePurchaseUc        : DeletePurchaseUc
) :  ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PurchaseViewModel(
                getPurchaseByIdUc        = getPurchaseByIdUc       ,
                getDaughterPurchasesUc   = getDaughterPurchasesUc  ,
                getSumDaughterPurchaseUc = getSumDaughterPurchaseUc,
                setPurchaseUc            = setPurchaseUc           ,
                deletePurchaseUc         = deletePurchaseUc
            ) as T
        }
}