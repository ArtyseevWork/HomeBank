package com.mordansoft.homebank.data.storage


import androidx.room.*
import com.mordansoft.homebank.data.model.ProfitD

@Dao
interface ProfitDao {

    @Query("SELECT * FROM profit")
    suspend fun getAllProfits(): Array<ProfitD>
    @Query("SELECT id FROM profit")
    suspend fun getAllId(): Array<Long>


    @Query("SELECT * FROM profit where statusId < 400")
    suspend fun getAllActiveProfits(): Array<ProfitD>

    @Query("SELECT * FROM profit where periodId = :periodId")
    suspend fun getMainProfits(periodId : Int): Array<ProfitD>


    @Query("SELECT * FROM profit WHERE id = :profitId")
    suspend fun getProfitById(profitId: Long): ProfitD

    @Insert
    suspend fun insertAll(vararg todo: ProfitD)

    @Delete
    suspend fun deleteProfit(profit: ProfitD)

    @Update
    suspend fun updateProfit(vararg profit: ProfitD)

}
