package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Profit

interface ProfitRepo {
    suspend fun updateProfit(profit: Profit)
    suspend fun getProfitById(profitId: Long): Profit
    suspend fun getMainProfits(periodId : Long): ArrayList<Profit>
    suspend fun insertTestProfit(){    }
}