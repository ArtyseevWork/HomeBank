package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo

class GetProfitByIdUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(profitId: Long): Profit{
        var profit : Profit = Profit()
        if (profitId > 0L){                                     // not new Profit
            profitRepo.getProfitById(profitId = profitId)
        }
        return profit
    }
}