package com.mordansoft.homebank.domain.usecase.period

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.repo.PeriodRepo
import com.mordansoft.homebank.domain.repo.ProfitRepo

class GetActivePeriodUc(private val periodRepo: PeriodRepo) {

    suspend fun execute() : Period{
        val activeStatus : Int = 100
        return periodRepo.getPeriodsByStatus(activeStatus)[0]

    }
}