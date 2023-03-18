package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo


class GetMainProfitsUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(): ArrayList<Profit> {
        profitRepo.insertTestProfit()
        val parentId : Long = -8
        val periodId : Int = -8
        return profitRepo.getMainProfits(periodId = periodId)
    }

}

