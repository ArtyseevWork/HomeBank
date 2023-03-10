package com.mordansoft.homebank.data.repo


import com.mordansoft.homebank.data.model.PurchaseD
import com.mordansoft.homebank.data.storage.PurchaseDao
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class PurchaseRepoImpl (private val purchaseDao: PurchaseDao) : PurchaseRepo {

    override fun deletePurchase(purchase: Purchase) {
        return purchaseDao.deletePurchase(purchaseToPurchaseD(purchase))
    }

    override fun updatePurchase(purchase: Purchase) {
        return purchaseDao.updatePurchase(purchaseToPurchaseD(purchase))
    }

    override fun getPurchaseById(purchaseId: Long): Purchase {
        return purchaseDToPurchase(purchaseDao.getPurchaseById(purchaseId));
    }

    override fun getPurchasesByQuery(query: String): ArrayList<Purchase> {
        return purchaseDToPurchaseArray(purchaseDao.getPurchaseByQuery(query));
    }

    override fun insertTestPurchase(){
        for ( i in 1..100){
            try{
                purchaseDao.insertAll(PurchaseD(id = i.toLong()))
            } catch (e: Exception){
                println("$i - id already exist")
            }

        }
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
            period      = purchase.period,
            status      = purchase.status,
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
            period      = purchaseD.period,
            status      = purchaseD.status,
            parentId    = purchaseD.parentId,
            repeater    = purchaseD.repeater,
            timestamp   = purchaseD.timestamp
        )
    }

    private fun purchaseDToPurchaseArray(arrayPurchasesD: List<PurchaseD>): ArrayList<Purchase> {
        var purchases = ArrayList<Purchase>()
        for (e in arrayPurchasesD) {
            purchases.add(purchaseDToPurchase(e))
        }
        return purchases
    }
    /********* ! mappers  ************/

}
