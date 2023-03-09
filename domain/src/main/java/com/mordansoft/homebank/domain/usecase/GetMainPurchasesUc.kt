package com.mordansoft.homebank.domain.usecase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetMainPurchasesUc(private val purchaseRepo: PurchaseRepo) {

    fun execute(): ArrayList<Purchase> {
        val parentId = -8
        return purchaseRepo.getPurchasesByQuery("parentId = $parentId")
    }
}