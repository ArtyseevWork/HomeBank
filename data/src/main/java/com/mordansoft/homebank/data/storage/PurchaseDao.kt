package com.mordansoft.homebank.data.storage


import androidx.room.*
import com.mordansoft.homebank.data.model.PurchaseD

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchase")
    suspend fun getAllPurchases(): Array<PurchaseD>
    @Query("SELECT id FROM purchase")
    suspend fun getAllId(): Array<Long>

    @Query("SELECT * FROM purchase where statusId < 400")
    suspend fun getAllActivePurchases(): Array<PurchaseD>

    @Query("SELECT * FROM purchase where parentId = :parentId and periodId = :periodId")
    suspend fun getMainPurchases(parentId : Long, periodId : Int): Array<PurchaseD>

    @Query("SELECT * FROM purchase where parentId = :parentId " +
                                    "and periodId = :periodId " +
                                    "and statusId = :statusId")
    suspend fun getMainPurchases(parentId : Long, periodId : Int, statusId : Int): Array<PurchaseD>


    @Query("SELECT * FROM purchase WHERE parentId = :parentId")
    suspend fun getDaughterPurchases(parentId: Long): Array<PurchaseD>

    @Query("SELECT * FROM purchase WHERE id = :purchaseId")
    suspend fun getPurchaseById(purchaseId: Long): PurchaseD

    @Insert
    suspend fun insertAll(vararg todo: PurchaseD)

    @Delete
    suspend fun deletePurchase(purchase: PurchaseD)

    @Update
    suspend fun updatePurchase(vararg purchase: PurchaseD)

}
