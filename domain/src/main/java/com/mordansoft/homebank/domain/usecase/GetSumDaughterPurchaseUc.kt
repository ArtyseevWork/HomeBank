package com.mordansoft.homebank.domain.usecase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetSumDaughterPurchaseUc(private val purchaseRepo: PurchaseRepo) {

    fun execute(purchase : Purchase): Float{
        val parentId = purchase.parentId
        val daughterPurchases = purchaseRepo.getPurchasesByQuery("parentId = $parentId")
        var purchasesSum: Float = 0f
        for (e in daughterPurchases){
            val count = e.count
            val price = e.price
            val eSum = count * price
            purchasesSum += eSum
        }
        return purchasesSum
    }
}