package com.mordansoft.homebank.data.repo


import androidx.annotation.WorkerThread
import com.mordansoft.homebank.data.model.PurchaseD
import com.mordansoft.homebank.data.storage.PreferencesStorageImplSnPr
import com.mordansoft.homebank.data.storage.PurchaseDao
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PreferencesRepo
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import kotlinx.coroutines.*

class PreferencesRepoImpl (private val preferencesStorageImplSnPr: PreferencesStorageImplSnPr,
                           private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) :
    PreferencesRepo {

    override suspend fun updatePreferences(preferences: Preferences) {
        preferencesStorageImplSnPr.updatePreferences(preferences)
    }

    override suspend fun getPreferences(): Preferences {
        return preferencesStorageImplSnPr.getPreferences()
    }

    /*********** mappers  ************/
    private fun purchaseToPurchaseD(purchase: Purchase): PurchaseD {
        return PurchaseD(
            id          = purchase.id,
            idFdb       = purchase.idFdb,
            name        = purchase.name,
            description = purchase.description,
            price       = purchase.price,
            count       = purchase.count,
            periodId    = purchase.periodId,
            statusId    = purchase.statusId,
            parentId    = purchase.parentId,
            repeater    = purchase.repeater,
            timestamp   = purchase.timestamp
        )
    }

    private fun purchaseDToPurchase(purchaseD: PurchaseD): Purchase {
        return Purchase(
            id          = purchaseD.id,
            idFdb       = purchaseD.idFdb,
            name        = purchaseD.name,
            description = purchaseD.description,
            price       = purchaseD.price,
            count       = purchaseD.count,
            periodId    = purchaseD.periodId,
            statusId    = purchaseD.statusId,
            parentId    = purchaseD.parentId,
            repeater    = purchaseD.repeater,
            timestamp   = purchaseD.timestamp
        )
    }

    private fun purchaseDToPurchaseArray(arrayPurchasesD: Array<PurchaseD>): ArrayList<Purchase> {
        val purchases = ArrayList<Purchase>()
        for (e in arrayPurchasesD) {
            purchases.add(purchaseDToPurchase(e))
        }
        return purchases
    }
    /********* ! mappers  ************/

}
