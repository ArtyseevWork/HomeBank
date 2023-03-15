package com.mordansoft.homebank.domain.usecase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetMainPurchasesUc(val purchaseRepo: PurchaseRepo) {

    suspend fun execute(): ArrayList<Purchase> {
       // val parentId = -8
       // val x = purchaseRepo.getPurchasesByQuery("parentId = $parentId")
        val x = purchaseRepo.getAllPurchases()
        return x
    }


}

