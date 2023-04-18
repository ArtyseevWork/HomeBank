package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo

class ProfitSyncUc (private val profitRepo: ProfitRepo) {

    suspend fun execute(profitFdb: Profit) {
        if (profitFdb.id > 0) {
            val profitSql: Profit = profitRepo.getProfitById(profitFdb.id)
            if (profitSql.timestamp > profitFdb.timestamp) {
                profitRepo.updateRemoteProfit(profitSql)
            } else if (profitSql.timestamp < profitFdb.timestamp) {
                profitRepo.updateProfit(profitFdb)
            }
        }
    }
}