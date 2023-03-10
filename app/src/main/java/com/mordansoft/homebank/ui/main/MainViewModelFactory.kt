package com.mordansoft.homebank.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider


class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {


   /* private val purchaseRepo: PurchaseRepo
    private val getMainPurchasesUc: GetMainPurchasesUc
    private val getSumDaughterPurchaseUc: GetSumDaughterPurchaseUc

    init {
        val app: App = App.getInstance()!!
        val db: AppDatabase? = app.getDatabase()

        if (db != null) {
            purchaseRepo = PurchaseRepoImplRoom(db.purchaseDao())
            getMainPurchasesUc = GetMainPurchasesUc(purchaseRepo)
            getSumDaughterPurchaseUc = GetSumDaughterPurchaseUc(purchaseRepo)
        }

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getMainPurchasesUc       = getMainPurchasesUc,
                             getSumDaughterPurchaseUc = getSumDaughterPurchaseUc) as T
    }

*/
}