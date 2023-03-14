package com.mordansoft.homebank.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.mordansoft.homebank.data.repo.PurchaseRepoImpl
import com.mordansoft.homebank.data.storage.AppDatabase
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import com.mordansoft.homebank.domain.usecase.GetMainPurchasesUc



class MainViewModelFactory(application: Application):  ViewModelProvider.Factory {


    private val purchaseRepo: PurchaseRepo
    private val getMainPurchasesUc: GetMainPurchasesUc

    init {
        purchaseRepo =
            PurchaseRepoImpl(AppDatabase.getDatabase(application).purchaseDao())
            getMainPurchasesUc = GetMainPurchasesUc(purchaseRepo)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getMainPurchasesUc = getMainPurchasesUc
        ) as T
    }
}