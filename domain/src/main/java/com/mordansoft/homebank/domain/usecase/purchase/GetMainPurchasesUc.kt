package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetMainPurchasesUc(val purchaseRepo: PurchaseRepo) {

    suspend fun execute(periodId : Int): ArrayList<Purchase> {
        purchaseRepo.insertTestPurchase()
        val parentId : Long = -8 //todo create constant
        return purchaseRepo.getMainPurchases(parentId = parentId,
                                             periodId = periodId)
    }

}

