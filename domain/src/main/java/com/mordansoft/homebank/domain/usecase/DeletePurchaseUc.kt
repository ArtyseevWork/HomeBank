package com.mordansoft.homebank.domain.usecase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class DeletePurchaseUc(private val purchaseRepo: PurchaseRepo) {

    fun execute(purchase: Purchase){
        return purchaseRepo.deletePurchase(purchase)
    }
}