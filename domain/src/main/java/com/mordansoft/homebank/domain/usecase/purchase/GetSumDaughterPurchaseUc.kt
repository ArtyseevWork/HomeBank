package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetSumDaughterPurchaseUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(parentId : Long): Float{
        var purchasesSum: Float = 0f
        if (parentId != 0L) {
            val daughterPurchases = purchaseRepo.getDaughterPurchases(parentId)

            for (e in daughterPurchases){
                val count = e.count
                val price = e.price
                val eSum = count * price
                purchasesSum += eSum
            }
        }
        return purchasesSum
    }
}