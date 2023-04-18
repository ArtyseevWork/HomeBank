package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mordansoft.homebank.domain.model.Purchase


@Entity(tableName = "purchase")
data class PurchaseD(
    @PrimaryKey (autoGenerate = false)
    val id: Long            = Purchase.DEFAULT_ID         ,
    val name: String        = Purchase.DEFAULT_NAME       ,
    val description: String = Purchase.DEFAULT_DESCRIPTION,
    val price: Float        = Purchase.DEFAULT_PRICE      ,
    val count: Float        = Purchase.DEFAULT_COUNT      ,
    val periodId: Int       = Purchase.DEFAULT_PERIOD_ID  ,
    val statusId: Int       = Purchase.DEFAULT_STATUS_ID  ,
    val parentId: Long      = Purchase.DEFAULT_PARENT_ID  ,
    val repeater: Boolean   = Purchase.DEFAULT_REPEATER   ,
    val timestamp: Long     = Purchase.DEFAULT_TIMESTAMP  ){
    companion object{
        /*********** mappers  ************/
        fun purchaseToPurchaseD(purchase: Purchase): PurchaseD {
            return PurchaseD(
                id          = purchase.id,
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

        fun purchaseDToPurchase(purchaseD: PurchaseD): Purchase {
            return Purchase(
                id          = purchaseD.id,
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

        fun purchaseDToPurchaseArray(arrayPurchasesD: Array<PurchaseD>): ArrayList<Purchase> {
            val purchases = ArrayList<Purchase>()
            for (e in arrayPurchasesD) {
                purchases.add(purchaseDToPurchase(e))
            }
            return purchases
        }
        /********* ! mappers  ************/
    }
}
