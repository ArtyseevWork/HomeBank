package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo


class GetMainProfitsUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(periodId : Int): ArrayList<Profit> {
        profitRepo.insertTestProfit()
        return profitRepo.getMainProfits(periodId = periodId)
    }

}

