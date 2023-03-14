package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Purchase

interface PurchaseRepo {
    suspend fun deletePurchase(purchase: Purchase)
    suspend fun updatePurchase(purchase: Purchase)
    suspend fun getPurchaseById(purchaseId: Long): Purchase
    suspend fun getPurchasesByQuery(query: String): ArrayList<Purchase>

    suspend fun getAllPurchases(): ArrayList<Purchase>


    suspend fun insertTestPurchase(){    }
}