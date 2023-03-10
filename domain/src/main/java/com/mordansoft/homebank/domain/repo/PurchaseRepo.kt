package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Purchase

interface PurchaseRepo {
    fun deletePurchase(purchase: Purchase)
    fun updatePurchase(purchase: Purchase)
    fun getPurchaseById(purchaseId: Long): Purchase
    fun getPurchasesByQuery(query: String): ArrayList<Purchase>

    fun insertTestPurchase(){    }
}