package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo

class SetProfitUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(profit: Profit){
        profitRepo.updateProfit(profit)
    }
    
}