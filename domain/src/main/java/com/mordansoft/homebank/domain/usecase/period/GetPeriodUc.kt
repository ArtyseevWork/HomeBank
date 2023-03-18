package com.mordansoft.homebank.domain.usecase.period

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.repo.PeriodRepo
import com.mordansoft.homebank.domain.repo.PreferencesRepo

class GetPeriodUc(private val periodRepo     : PeriodRepo,
                  private val preferencesRepo: PreferencesRepo) {

    suspend fun execute(periodId : Int?) : Period{
        var _periodId: Int? = periodId;
        var period: Period
        val previousPeriod: Period
        val nextPeriod: Period
        if (_periodId == null) {
            _periodId = preferencesRepo.getPreferences().activePeriod
        }
        period         = periodRepo.getPeriodById(periodId = _periodId)
        previousPeriod = periodRepo.getPeriodById(periodId = _periodId -1) // previous neighbor
        nextPeriod     = periodRepo.getPeriodById(periodId = _periodId +1) // next neighbor
        period.previousPeriodId = previousPeriod.id // if neighbor is disable id == default id (0)
        period.nextPeriodId     = nextPeriod.id     // if neighbor is disable id == default id (0)
        return period
    }
}