package com.mordansoft.homebank.data.storage

import androidx.room.*
import com.mordansoft.homebank.data.model.PurchaseD

@Dao
interface PurchaseDao {


    @Query("SELECT * FROM purchase WHERE :query")
    fun getPurchaseByQuery(query: String): ArrayList<PurchaseD>

    @Query("SELECT * FROM purchase")
    fun getAll(): List<PurchaseD>

    @Query("SELECT * FROM purchase WHERE id = :purchaseId")
    fun getPurchaseById(purchaseId: Long): PurchaseD

    @Insert
    fun insertAll(vararg todo: PurchaseD)

    @Delete
    fun deletePurchase(purchase: PurchaseD)

    @Update
    fun updatePurchase(vararg purchase: PurchaseD)

}
