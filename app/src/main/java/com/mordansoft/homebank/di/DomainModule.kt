package com.mordansoft.homebank.di

import com.mordansoft.homebank.domain.repo.PurchaseRepo
import com.mordansoft.homebank.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetMainPurchasesUc(purchaseRepo : PurchaseRepo) : GetMainPurchasesUc {
        return GetMainPurchasesUc(purchaseRepo)
    }

    @Provides
    fun provideGetPurchaseByIdUc(purchaseRepo: PurchaseRepo) : GetPurchaseByIdUc{
        return GetPurchaseByIdUc(purchaseRepo)
    }
    @Provides
    fun provideGetDaughterPurchasesUc(purchaseRepo: PurchaseRepo) : GetDaughterPurchasesUc{
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
    fun provideDeletePurchaseUc(purchaseRepo: PurchaseRepo) : DeletePurchaseUc{
        return DeletePurchaseUc(purchaseRepo)
    }
}