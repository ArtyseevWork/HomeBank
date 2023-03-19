package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetMainPurchasesUc(val purchaseRepo: PurchaseRepo) {

    suspend fun execute(periodId : Int, statusId : Int = 0 ): ArrayList<Purchase> {
        purchaseRepo.insertTestPurchase()
        val parentId : Long = -8 //todo create constant

        if (statusId == 0 ) {
            return purchaseRepo.getMainPurchases(
                parentId = parentId,
                periodId = periodId
            )
        } else {
            return purchaseRepo.getMainPurchases(
                parentId = parentId,
                periodId = periodId,
                statusId = statusId)
        }


    }

}

