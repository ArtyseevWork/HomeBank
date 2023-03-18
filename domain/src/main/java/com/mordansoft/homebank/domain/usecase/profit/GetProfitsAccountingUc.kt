package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.ProfitsAccounting
import com.mordansoft.homebank.domain.repo.ProfitRepo

class GetProfitsAccountingUc( private val profitRepo: ProfitRepo) {

    suspend fun execute(periodId : Int): ProfitsAccounting {

        var capitalFact  : Float = 0F
        var capitalPlan  : Float = 0F


        var periodProfits = profitRepo.getMainProfits(periodId = periodId)

        for (profit in  periodProfits){ //todo refactor to when
            if (profit.statusId < 400 ) {
                capitalPlan += profit.amount
                if (profit.statusId == 300) {
                    capitalFact += profit.amount
                }
            }
        }

        return ProfitsAccounting(
            capitalFact = capitalFact ,
            capitalPlan = capitalPlan ,
            periodId    =  periodId
        )

    }
}