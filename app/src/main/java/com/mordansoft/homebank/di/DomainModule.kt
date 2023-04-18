package com.mordansoft.homebank.di

import com.mordansoft.homebank.domain.repo.*
import com.mordansoft.homebank.domain.usecase.exchange_rates.GetExchangeRatesUc
import com.mordansoft.homebank.domain.usecase.period.GetPeriodAccountingUc
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.period.PeriodSyncUc
import com.mordansoft.homebank.domain.usecase.preferences.PreferencesSyncUc
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

    @Provides
    fun provideGetExchangeRatesUc(exchangeRepo: ExchangeRepo,
                                  preferencesRepo: PreferencesRepo) : GetExchangeRatesUc {
        return GetExchangeRatesUc(exchangeRepo    = exchangeRepo,
                                  preferencesRepo = preferencesRepo)
    }

    @Provides
    fun provideProfitSyncUc(profitRepo : ProfitRepo) : ProfitSyncUc {
        return ProfitSyncUc(profitRepo = profitRepo)
    }

    @Provides
    fun providePurchaseSyncUc(purchaseRepo : PurchaseRepo) : PurchaseSyncUc {
        return PurchaseSyncUc(purchaseRepo = purchaseRepo)
    }

    @Provides
    fun providePeriodSyncUc(periodRepo : PeriodRepo) : PeriodSyncUc {
        return PeriodSyncUc(periodRepo = periodRepo)
    }

    @Provides
    fun providePreferencesSyncUc(preferencesRepo : PreferencesRepo) : PreferencesSyncUc {
        return PreferencesSyncUc(preferencesRepo = preferencesRepo)
    }


}