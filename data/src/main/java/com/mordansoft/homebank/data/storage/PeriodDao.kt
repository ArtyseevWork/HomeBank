package com.mordansoft.homebank.data.storage


import androidx.room.*
import com.mordansoft.homebank.data.model.PeriodD

@Dao
interface PeriodDao {

    @Query("SELECT * FROM period")
    suspend fun getAllPeriods(): Array<PeriodD>
    @Query("SELECT id FROM period")
    suspend fun getAllId(): Array<Int>

    @Query("SELECT * FROM period where statusId = :statusId")
    suspend fun getPeriodsByStatus(statusId: Int): Array<PeriodD>

    @Query("SELECT * FROM period WHERE id = :periodId")
    suspend fun getPeriodById(periodId: Int): PeriodD


    @Insert
    suspend fun insertAll(vararg todo: PeriodD)

    @Delete
    suspend fun deletePeriod(period: PeriodD)

    @Update
    suspend fun updatePeriod(vararg period: PeriodD)

}
