package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Profit

interface ProfitRepo {
    suspend fun updateProfit(profit: Profit)
    suspend fun getProfitById(profitId: Long): Profit
    suspend fun getMainProfits(periodId : Int): ArrayList<Profit>
    suspend fun getAllProfits(): ArrayList<Profit>
    suspend fun getAllId(): Array<Long>
    suspend fun insertProfit(profit: Profit)
    suspend fun insertTestProfit(){    }
    fun updateRemoteProfit(profit: Profit)
}