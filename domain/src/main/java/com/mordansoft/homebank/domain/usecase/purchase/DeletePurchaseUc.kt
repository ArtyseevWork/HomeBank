package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class DeletePurchaseUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchase: Purchase){
        if (purchase.id != 0L){
            purchase.statusId = 400
            purchaseRepo.updatePurchase(purchase)
        }
    }
}