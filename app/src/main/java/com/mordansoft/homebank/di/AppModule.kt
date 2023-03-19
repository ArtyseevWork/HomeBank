package com.mordansoft.homebank.di

import android.content.Context
import com.mordansoft.homebank.domain.usecase.period.GetPeriodAccountingUc
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.profit.*
import com.mordansoft.homebank.domain.usecase.purchase.*
import com.mordansoft.homebank.ui.main.MainViewModelFactory
import com.mordansoft.homebank.ui.profit.ProfitViewModelFactory
import com.mordansoft.homebank.ui.profits.ProfitsViewModelFactory
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
    fun provideMainViewModelFactory(getMainPurchasesUc    : GetMainPurchasesUc,
                                    getPeriodAccountingUc : GetPeriodAccountingUc,
                                    getPeriodUc           : GetPeriodUc
                                    ) : MainViewModelFactory{
        return MainViewModelFactory(getMainPurchasesUc, getPeriodAccountingUc, getPeriodUc)
    }

    @Provides
    fun provideProfitsViewModelFactory(getMainProfitsUc       : GetMainProfitsUc,
                                       getProfitsAccountingUc : GetProfitsAccountingUc,
                                       getPeriodUc            : GetPeriodUc
    )
    : ProfitsViewModelFactory {
        return ProfitsViewModelFactory(getMainProfitsUc, getProfitsAccountingUc, getPeriodUc)
    }

    @Provides
    fun provideProfitViewModelFactory(getProfitByIdUc : GetProfitByIdUc,
                                      setProfitUc     : SetProfitUc,
                                      deleteProfitUc  : DeleteProfitUc
    ) : ProfitViewModelFactory {
        return ProfitViewModelFactory(getProfitByIdUc, setProfitUc, deleteProfitUc)
    }

    @Provides
    fun providePurchaseViewModelFactory(getPurchaseByIdUc        : GetPurchaseByIdUc,
                                        getDaughterPurchasesUc   : GetDaughterPurchasesUc,
                                        getSumDaughterPurchaseUc : GetSumDaughterPurchaseUc,
                                        setPurchaseUc            : SetPurchaseUc,
                                        deletePurchaseUc         : DeletePurchaseUc
                                        ) : PurchaseViewModelFactory{
        return PurchaseViewModelFactory(getPurchaseByIdUc,
                                        getDaughterPurchasesUc,
                                        getSumDaughterPurchaseUc,
                                        setPurchaseUc,
                                        deletePurchaseUc
        )
    }


}