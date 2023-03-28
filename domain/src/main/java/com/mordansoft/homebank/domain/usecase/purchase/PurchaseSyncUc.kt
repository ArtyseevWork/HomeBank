package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class PurchaseSyncUc (private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchaseFdb: Purchase) {
        val purchaseSql: Purchase = purchaseRepo.getPurchaseById(purchaseFdb.id)

        if (purchaseSql.timestamp > purchaseFdb.timestamp) {
            purchaseRepo.updateRemotePurchase(purchaseSql)
        } else if (purchaseSql.timestamp < purchaseFdb.timestamp) {
            purchaseRepo.updatePurchase(purchaseFdb)
        }
    }
}