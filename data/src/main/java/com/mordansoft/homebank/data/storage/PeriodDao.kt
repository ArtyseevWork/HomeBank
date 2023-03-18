package com.mordansoft.homebank.data.storage


import androidx.room.*
import com.mordansoft.homebank.data.model.PeriodD

@Dao
interface PeriodDao {

    @Query("SELECT * FROM period where statusId = :statusId")
    suspend fun getPeriodsByStatus(statusId: Int): Array<PeriodD>

    @Query("SELECT * FROM period WHERE id = :periodId")
    suspend fun getPeriodById(periodId: Long): PeriodD


    @Insert
    suspend fun insertAll(vararg todo: PeriodD)

    @Delete
    suspend fun deletePeriod(period: PeriodD)

    @Update
    suspend fun updatePeriod(vararg period: PeriodD)

}
