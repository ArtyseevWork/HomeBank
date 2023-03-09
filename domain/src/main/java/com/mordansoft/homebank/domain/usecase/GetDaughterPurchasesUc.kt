package com.mordansoft.homebank.domain.usecase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetDaughterPurchasesUc(private val purchaseRepo: PurchaseRepo) {

    fun execute(purchase: Purchase): ArrayList<Purchase> {
        val parentId = purchase.parentId
        return purchaseRepo.getPurchasesByQuery("parentId = $parentId")
    }
}