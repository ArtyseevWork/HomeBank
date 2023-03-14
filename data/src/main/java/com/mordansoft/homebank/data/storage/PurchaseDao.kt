package com.mordansoft.homebank.data.storage


import androidx.room.*
import com.mordansoft.homebank.data.model.PurchaseD

@Dao
interface PurchaseDao {


    @Query("SELECT * FROM purchase WHERE :query")
    suspend fun getPurchaseByQuery(query: String): List<PurchaseD>

    @Query("SELECT * FROM purchase")
    suspend fun getAll(): List<PurchaseD>

    @Query("SELECT * FROM purchase WHERE id = :purchaseId")
    suspend fun getPurchaseById(purchaseId: Long): PurchaseD

    @Insert
    suspend fun insertAll(vararg todo: PurchaseD)

    @Delete
    suspend fun deletePurchase(purchase: PurchaseD)

    @Update
    suspend fun updatePurchase(vararg purchase: PurchaseD)

}
