package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetPurchaseByIdUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchaseId: Long): Purchase{
        return purchaseRepo.getPurchaseById(purchaseId = purchaseId)
    }
}