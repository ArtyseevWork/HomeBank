package com.mordansoft.homebank.data.repo


import com.mordansoft.homebank.data.model.PeriodD
import com.mordansoft.homebank.data.storage.PeriodDao
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.repo.PeriodRepo
import kotlinx.coroutines.*

class PeriodRepoImpl (private val periodDao: PeriodDao,
                      private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : PeriodRepo {

    override suspend fun updatePeriod(period: Period) {
        return periodDao.updatePeriod(periodToPeriodD(period))
    }



    override suspend fun getPeriodsByStatus(statusId : Int): ArrayList<Period> {
        return periodDToPeriodArray(periodDao.getPeriodsByStatus(statusId))
    }

    override suspend fun getPeriodById(periodId: Long): Period {
        return periodDToPeriod(periodDao.getPeriodById(periodId));
    }


    override suspend fun insertTestPeriod(){
        for ( i in 1..10){
            try{
                periodDao.insertAll(PeriodD(id = i))
            } catch (e: Exception){
                println("$i - id already exist")
            }
        }
    }


    /*********** mappers  ************/
    private fun periodToPeriodD(period: Period): PeriodD {
        return PeriodD(
            id          = period.id      ,
            name        = period.name    ,
            statusId    = period.statusId,
            dateFrom    = period.dateFrom,
            dateTo      = period.dateTo
        )
    }

    private fun periodDToPeriod(periodD: PeriodD): Period {
        return Period(
             id          = periodD.id      ,
             name        = periodD.name    ,
             statusId    = periodD.statusId,
             dateFrom    = periodD.dateFrom,
             dateTo      = periodD.dateTo
        )
    }

    private fun periodDToPeriodArray(arrayPeriodsD: Array<PeriodD>): ArrayList<Period> {
        val periods = ArrayList<Period>()
        for (e in arrayPeriodsD) {
            periods.add(periodDToPeriod(e))
        }
        return periods
    }
    /********* ! mappers  ************/

}
