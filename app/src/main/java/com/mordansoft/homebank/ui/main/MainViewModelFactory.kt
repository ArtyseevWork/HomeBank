package com.mordansoft.homebank.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.mordansoft.homebank.data.repo.PurchaseRepoImpl
import com.mordansoft.homebank.data.storage.AppDatabase
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import com.mordansoft.homebank.domain.usecase.GetMainPurchasesUc



class MainViewModelFactory(val getMainPurchasesUc: GetMainPurchasesUc):  ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getMainPurchasesUc = getMainPurchasesUc
        ) as T
    }
}