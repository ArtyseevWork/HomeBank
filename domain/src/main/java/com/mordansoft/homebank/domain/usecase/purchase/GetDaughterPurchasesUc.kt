package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetDaughterPurchasesUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(parentId: Long): ArrayList<Purchase> {
        var result :ArrayList<Purchase>
        if (parentId != Purchase.DEFAULT_ID ){ // not new purchase
            result = purchaseRepo.getDaughterPurchases(parentId)
        } else {
            result = ArrayList() // return empty list
        }
        return result
    }
}