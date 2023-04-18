package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mordansoft.homebank.domain.model.Period
import java.util.ArrayList

@Entity(tableName = "period")
data class PeriodD(
    @PrimaryKey(autoGenerate = false)
    val id        : Int    = Period.DEFAULT_ID,
    val name      : String = Period.DEFAULT_NAME,
    val dateFrom  : Long   = Period.DEFAULT_DATE_FROM,
    val dateTo    : Long   = Period.DEFAULT_DATE_TO,
    val statusId  : Int    = Period.DEFAULT_STATUS_ID,
    var timestamp : Long   = Period.DEFAULT_TIMESTAMP)
{
    companion object{
        /*********** mappers  ************/
        fun periodToPeriodD(period: Period): PeriodD {
            return PeriodD(
                id          = period.id      ,
                name        = period.name    ,
                statusId    = period.statusId,
                dateFrom    = period.dateFrom,
                dateTo      = period.dateTo
            )
        }

        fun periodDToPeriod(periodD: PeriodD?): Period {

            if (periodD == null){
                return Period()
            } else {
                return Period(
                    id       = periodD.id,
                    name     = periodD.name,
                    statusId = periodD.statusId,
                    dateFrom = periodD.dateFrom,
                    dateTo   = periodD.dateTo
                )
            }
        }

        fun periodDToPeriodArray(arrayPeriodsD: Array<PeriodD>): ArrayList<Period> {
            val periods = ArrayList<Period>()
            for (e in arrayPeriodsD) {
                periods.add(periodDToPeriod(e))
            }
            return periods
        }
        /********* ! mappers  ************/
    }
}
