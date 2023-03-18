package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Period

interface PeriodRepo {
    suspend fun updatePeriod(period: Period)
    suspend fun getPeriodById(periodId: Long): Period
    suspend fun getPeriodsByStatus(statusId : Int): ArrayList<Period>
    suspend fun insertTestPeriod(){    }
}