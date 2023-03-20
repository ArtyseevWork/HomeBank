package com.mordansoft.homebank.data.repo


import androidx.annotation.WorkerThread
import com.mordansoft.homebank.data.model.PurchaseD
import com.mordansoft.homebank.data.storage.PurchaseDao
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import kotlinx.coroutines.*

class PurchaseRepoImpl (private val purchaseDao: PurchaseDao,
                        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : PurchaseRepo {

    override suspend fun updatePurchase(purchase: Purchase) {
        return purchaseDao.updatePurchase(purchaseToPurchaseD(purchase))
    }

    override suspend fun getMainPurchases(parentId: Long, periodId: Int): ArrayList<Purchase> {
        var resultList: ArrayList<Purchase>
        withContext(defaultDispatcher) {
            resultList = purchaseDToPurchaseArray(purchaseDao.getMainPurchases(parentId = parentId,
                                                                               periodId = periodId))
        }
        return resultList
    }

    override suspend fun getPurchaseById(purchaseId: Long): Purchase {
        return purchaseDToPurchase(purchaseDao.getPurchaseById(purchaseId));
    }


    override suspend fun getDaughterPurchases(parentId : Long): ArrayList<Purchase> {
        var resultList: ArrayList<Purchase>
        withContext(defaultDispatcher) {
            resultList = purchaseDToPurchaseArray(purchaseDao.getDaughterPurchases(parentId));
        }
        return resultList
    }

    override suspend fun insertPurchase(purchase: Purchase) {
        withContext(defaultDispatcher) {
            purchaseDao.insertAll(purchaseToPurchaseD(purchase))
        }
    }

    override suspend fun insertTestPurchase(){
        for ( i in 1..8){
            try{
                purchaseDao.insertAll(PurchaseD(id = i.toLong()))
            } catch (e: Exception){
                println("$i - id already exist")
            }
        }
    }

    override suspend fun getMainPurchases(
        parentId: Long,
        periodId: Int,
        statusId: Int
    ): ArrayList<Purchase> {
        var resultList: ArrayList<Purchase>
        withContext(defaultDispatcher) {
            resultList = purchaseDToPurchaseArray(purchaseDao.getMainPurchases(parentId = parentId,
                                                                               periodId = periodId,
                                                                               statusId = statusId))
        }
        return resultList
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
