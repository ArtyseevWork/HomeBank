package com.mordansoft.homebank.data.repo


import androidx.annotation.WorkerThread
import com.mordansoft.homebank.data.model.PurchaseD
import com.mordansoft.homebank.data.model.PurchaseD.Companion.purchaseDToPurchase
import com.mordansoft.homebank.data.model.PurchaseD.Companion.purchaseDToPurchaseArray
import com.mordansoft.homebank.data.model.PurchaseD.Companion.purchaseToPurchaseD
import com.mordansoft.homebank.data.storage.PurchaseDao
import com.mordansoft.homebank.data.storage.firebase.FdbStorageImpl
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import kotlinx.coroutines.*

class PurchaseRepoImpl (private val purchaseDao      : PurchaseDao,
                        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default)
    : PurchaseRepo {


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

    override suspend fun getAllPurchases(): ArrayList<Purchase> {
        var resultList: ArrayList<Purchase>
        withContext(defaultDispatcher) {
            resultList = purchaseDToPurchaseArray(purchaseDao.getAllPurchases());
        }
        return resultList
    }

    override suspend fun getAllId(): Array<Long> {
        return purchaseDao.getAllId()
    }

    override suspend fun insertPurchase(purchase: Purchase) {
        withContext(defaultDispatcher) {
            purchaseDao.insertAll(purchaseToPurchaseD(purchase))
        }
    }

    override suspend fun insertTestPurchase(){
        /*for ( i in 1..8){
            try{
                purchaseDao.insertAll(PurchaseD(id = i.toLong()))
            } catch (e: Exception){
                println("$i - id already exist")
            }
        }*/
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

    override suspend fun updateRemotePurchase(purchase: Purchase) {
        val userId : String? = FdbStorageImpl.getUserId()
        if (userId != null){
            val dbPurchaseReference =
                FdbStorageImpl.getReference(FdbStorageImpl.Folders.Purchase)
            dbPurchaseReference
                .child(purchase.id.toString())
                .setValue(purchaseToPurchaseD(purchase))
        }

    }



}
