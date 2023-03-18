package com.mordansoft.homebank.domain.usecase.period

import com.mordansoft.homebank.domain.model.PeriodAccounting
import com.mordansoft.homebank.domain.repo.PreferencesRepo
import com.mordansoft.homebank.domain.repo.ProfitRepo
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class GetPeriodAccountingUc(private val profitRepo: ProfitRepo,
                            private val purchaseRepo: PurchaseRepo,
                            private val preferencesRepo: PreferencesRepo) {
    suspend fun execute(periodId : Int): PeriodAccounting{

        var capitalFact  : Float = 0F
        var capitalPlan  : Float = 0F
        var expencesPlan : Float = 0F
        var expencesFact : Float = 0F
        var balancePlan  : Float = 0F
        var balanceFact  : Float = 0F

        var periodOfProfits = periodId
        if(preferencesRepo.getPreferences().profitsMode == 1){
            periodOfProfits -- //get profits of previous period
        }
        var periodProfits = profitRepo.getMainProfits(periodId = periodOfProfits)

        for (profit in  periodProfits){ //todo refactor to when
            if (profit.statusId < 400 ) {
                capitalPlan += profit.amount
                if (profit.statusId == 300) {
                    capitalFact += profit.amount
                }
            }
        }

        var periodPurchases = purchaseRepo.getMainPurchases(periodId = periodId, parentId = -8) // todo period-1
        for (purchase in  periodPurchases){
            if (purchase.statusId < 400){
                val purchaseAmount : Float  = purchase.count * purchase.price
                expencesPlan += purchaseAmount
                if (purchase.statusId == 300) {
                    expencesFact += purchaseAmount
                }
            }
        }

        balancePlan = capitalPlan - expencesPlan
        balanceFact = capitalFact - expencesFact

        return PeriodAccounting(
            capitalFact = capitalFact ,
            capitalPlan = capitalPlan ,
            expencesPlan= expencesPlan,
            expencesFact= expencesFact,
            balancePlan = balancePlan ,
            balanceFact = balanceFact
        )

    }


}

