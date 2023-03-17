package com.mordansoft.homebank.data.storage

import com.mordansoft.homebank.data.model.PurchaseD


interface ProfitStorage {
    fun deletePurchase(purchase: PurchaseD): Boolean
    fun updatePurchase(purchase: PurchaseD): Boolean
    fun getPurchaseById(purchaseId: Long): PurchaseD
    fun getPurchaseByQuery(query: String): List<PurchaseD>
}