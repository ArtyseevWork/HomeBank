package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Purchase

interface PurchaseRepo {
    suspend fun updatePurchase(purchase: Purchase)
    suspend fun getPurchaseById(purchaseId: Long): Purchase
    suspend fun getDaughterPurchases(parentId : Long): ArrayList<Purchase>
    suspend fun getMainPurchases(parentId : Long, periodId : Int): ArrayList<Purchase>
    suspend fun getMainPurchases(parentId : Long, periodId : Int, statusId : Int): ArrayList<Purchase>
    suspend fun insertPurchase(purchase: Purchase)
    suspend fun insertTestPurchase(){    }
    suspend fun updateRemotePurchase(purchase: Purchase)
}