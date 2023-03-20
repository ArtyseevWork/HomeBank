package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class DeletePurchaseUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchase: Purchase){
        if (purchase.id != Purchase.DEFAULT_ID){ // not new purchase
            purchase.statusId = Purchase.STATUS_REMOVED
            purchaseRepo.updatePurchase(purchase)
        }
    }
}