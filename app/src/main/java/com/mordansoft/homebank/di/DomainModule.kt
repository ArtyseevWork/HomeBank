package com.mordansoft.homebank.di

import com.mordansoft.homebank.domain.repo.PeriodRepo
import com.mordansoft.homebank.domain.repo.PreferencesRepo
import com.mordansoft.homebank.domain.repo.ProfitRepo
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import com.mordansoft.homebank.domain.usecase.period.GetPeriodAccountingUc
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.profit.*
import com.mordansoft.homebank.domain.usecase.purchase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetMainPurchasesUc(purchaseRepo : PurchaseRepo) : GetMainPurchasesUc {
        return GetMainPurchasesUc(purchaseRepo)
    }

    @Provides
    fun provideGetPurchaseByIdUc(purchaseRepo: PurchaseRepo) : GetPurchaseByIdUc {
        return GetPurchaseByIdUc(purchaseRepo)
    }
    @Provides
    fun provideGetDaughterPurchasesUc(purchaseRepo: PurchaseRepo) : GetDaughterPurchasesUc {
        return GetDaughterPurchasesUc(purchaseRepo)
    }
    @Provides
    fun provideGetSumDaughterPurchaseUc(purchaseRepo: PurchaseRepo) : GetSumDaughterPurchaseUc {
        return GetSumDaughterPurchaseUc(purchaseRepo)
    }
    @Provides
    fun provideSetPurchaseUc(purchaseRepo: PurchaseRepo) : SetPurchaseUc {
        return SetPurchaseUc(purchaseRepo)
    }
    @Provides
    fun provideDeletePurchaseUc(purchaseRepo: PurchaseRepo) : DeletePurchaseUc {
        return DeletePurchaseUc(purchaseRepo)
    }

    @Provides
    fun provideDeleteProfitUc(profitRepo: ProfitRepo) : DeleteProfitUc {
        return DeleteProfitUc(profitRepo)
    }

    @Provides
    fun provideGetProfitByIdUc(profitRepo: ProfitRepo) : GetProfitByIdUc {
        return GetProfitByIdUc(profitRepo)
    }

    @Provides
    fun provideGetMainProfitsUc(profitRepo : ProfitRepo) : GetMainProfitsUc {
        return GetMainProfitsUc(profitRepo)
    }

    @Provides
    fun provideSetProfitUc(profitRepo: ProfitRepo) : SetProfitUc {
        return SetProfitUc(profitRepo)
    }
    @Provides
    fun provideGetPeriodAccountingUc(profitRepo     : ProfitRepo,
                                     purchaseRepo   : PurchaseRepo,
                                     preferencesRepo: PreferencesRepo) : GetPeriodAccountingUc {
        return GetPeriodAccountingUc(
            profitRepo = profitRepo,
            purchaseRepo = purchaseRepo,
            preferencesRepo = preferencesRepo,
        )
    }
    @Provides
    fun provideGetProfitsAccountingUc(profitRepo : ProfitRepo) : GetProfitsAccountingUc {
        return GetProfitsAccountingUc(profitRepo = profitRepo)
    }

    @Provides
    fun provideGetPeriodUc(periodRepo   : PeriodRepo,
                           preferencesRepo: PreferencesRepo) : GetPeriodUc {
        return GetPeriodUc(periodRepo      = periodRepo,
                           preferencesRepo = preferencesRepo)
    }


}