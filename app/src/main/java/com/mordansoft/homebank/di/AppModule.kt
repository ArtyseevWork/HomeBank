package com.mordansoft.homebank.di

import android.content.Context
import com.mordansoft.homebank.domain.usecase.*
import com.mordansoft.homebank.ui.main.MainViewModelFactory
import com.mordansoft.homebank.ui.purchase.PurchaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext() : Context{
        return context
    }
    @Provides
    fun provideMainViewModelFactory(getMainPurchasesUc: GetMainPurchasesUc) : MainViewModelFactory{
        return MainViewModelFactory(getMainPurchasesUc)
    }

    @Provides
    fun providePurchaseViewModelFactory(getPurchaseByIdUc       : GetPurchaseByIdUc,
                                        getDaughterPurchasesUc  : GetDaughterPurchasesUc,
                                        getSumDaughterPurchaseUc: GetSumDaughterPurchaseUc,
                                        setPurchaseUc           : SetPurchaseUc,
                                        deletePurchaseUc        : DeletePurchaseUc
                                        ) : PurchaseViewModelFactory{
        return PurchaseViewModelFactory(getPurchaseByIdUc,
                                        getDaughterPurchasesUc,
                                        getSumDaughterPurchaseUc,
                                        setPurchaseUc,
                                        deletePurchaseUc
        )
    }


}