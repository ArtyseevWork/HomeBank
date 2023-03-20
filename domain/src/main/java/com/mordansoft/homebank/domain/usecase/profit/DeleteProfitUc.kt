package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo

class DeleteProfitUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(profit: Profit){
        if (profit.id != Profit.DEFAULT_ID){ //not new Profit
            profit.statusId = Profit.STATUS_REMOVED
            profitRepo.updateProfit(profit)
        }
    }
}