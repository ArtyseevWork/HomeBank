package com.mordansoft.homebank.domain.usecase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class SetPurchaseUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchase: Purchase){
        purchaseRepo.updatePurchase(purchase)
    }
}