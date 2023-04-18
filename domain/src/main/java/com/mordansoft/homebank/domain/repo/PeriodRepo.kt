package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Profit

interface PeriodRepo {
    suspend fun updatePeriod(period: Period)
    suspend fun getPeriodById(periodId: Int): Period
    suspend fun getAllPeriods(): ArrayList<Period>
    suspend fun getAllId(): Array<Int>
    suspend fun getPeriodsByStatus(statusId : Int): ArrayList<Period>
    suspend fun insertTestPeriod(){    }
    suspend fun updateRemotePeriod(period: Period)
}