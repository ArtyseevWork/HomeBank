package com.mordansoft.homebank.di

import com.mordansoft.homebank.domain.repo.PurchaseRepo
import com.mordansoft.homebank.domain.usecase.GetMainPurchasesUc
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetMainPurchasesUc(purchaseRepo : PurchaseRepo) : GetMainPurchasesUc {
        return GetMainPurchasesUc(purchaseRepo)
    }

}