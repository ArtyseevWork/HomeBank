package com.mordansoft.homebank.domain.usecase.period

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.repo.PeriodRepo

class PeriodSyncUc (private val periodRepo: PeriodRepo) {

    suspend fun execute(periodFdb: Period) {
        val periodSql: Period = periodRepo.getPeriodById(periodFdb.id)

        if (periodSql.timestamp > periodFdb.timestamp) {
            periodRepo.updateRemotePeriod(periodSql)
        } else if (periodSql.timestamp < periodFdb.timestamp) {
            periodRepo.updatePeriod(periodFdb)
        }
    }
}