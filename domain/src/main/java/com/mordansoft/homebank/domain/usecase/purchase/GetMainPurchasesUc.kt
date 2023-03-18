package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetMainPurchasesUc(val purchaseRepo: PurchaseRepo) {

    suspend fun execute(): ArrayList<Purchase> {
        purchaseRepo.insertTestPurchase()
        val parentId : Long = -8
        val periodId : Int = -8
        return purchaseRepo.getMainPurchases(parentId = parentId,
                                             periodId = periodId)
    }

}

