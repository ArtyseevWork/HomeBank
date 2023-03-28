package com.mordansoft.homebank.data.repo


import com.mordansoft.homebank.data.model.PeriodD
import com.mordansoft.homebank.data.storage.PeriodDao
import com.mordansoft.homebank.data.storage.firebase.FdbStorageImpl
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.repo.PeriodRepo
import kotlinx.coroutines.*
import java.util.*

class PeriodRepoImpl (private val periodDao: PeriodDao,
                      private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : PeriodRepo {

    override suspend fun updatePeriod(period: Period) {
        return periodDao.updatePeriod(periodToPeriodD(period))
    }

    override suspend fun getPeriodsByStatus(statusId : Int): ArrayList<Period> {
        return periodDToPeriodArray(periodDao.getPeriodsByStatus(statusId))
    }

    override suspend fun getPeriodById(periodId: Int): Period {
        insertTestPeriod()
        val periodD: PeriodD = periodDao.getPeriodById(periodId)
        val periodId2 = periodId
        return periodDToPeriod(periodD);
    }

    override suspend fun insertTestPeriod(){
       /* for ( i in 1..10){
            try{
                periodDao.insertAll(PeriodD(id = i))
            } catch (e: Exception){
                println("$i - id already exist")
            }
        }*/

            val count = 10 //Сколько периодов добавить
            val cal: Calendar = GregorianCalendar(2023, 0, 1) // Даты первого периода
            var startMonth: Long
            var endMonth: Long
            for (i in 1..count) {
                cal[Calendar.DAY_OF_MONTH] = 1
                val firstDay = cal.time
                cal[Calendar.DATE] = cal.getActualMaximum(Calendar.DATE)
                cal.add(Calendar.SECOND, 86399) //getting 23:59
                //cal.add(Calendar.SECOND,3); //добавляем секунд до конца дня
                val endDay = cal.time
                startMonth = firstDay.time
                endMonth = endDay.time
                val monthNames = arrayOf(
                    "Январь",
                    "Февраль",
                    "Март",
                    "Апрель",
                    "Май",
                    "Июнь",
                    "Июль",
                    "Август",
                    "Сентябрь",
                    "Октябрь",
                    "Ноябрь",
                    "Декабрь"
                )
                val monthName = monthNames[cal[Calendar.MONTH]]

                /*
            Month month = Month.of(cal.get(Calendar.MONTH) + 1);
            Locale loc = Locale.forLanguageTag("ru");
            String monthName = (month.getDisplayName(TextStyle.FULL_STANDALONE, loc)); // Не работает с API н же 26
            */
                var periodName = monthName + " " + cal[Calendar.YEAR]
                periodName = periodName.substring(0, 1)
                    .uppercase(Locale.getDefault()) + periodName.substring(1)
                var periodD  = PeriodD(
                    id = i,
                            name = periodName,
                            dateFrom = startMonth,
                            dateTo = endMonth,
                            statusId = 0
                )
                try{
                    periodDao.insertAll(periodD)
                    FdbStorageImpl.updatePeriod(periodD)
                } catch (e: Exception){
                    println("$i - id already exist")
                }
                cal.add(Calendar.SECOND, -86399) //reset to 12:00AM (00:00)
                cal.add(Calendar.MONTH, 1)
            }

    }

    override suspend fun updateRemotePeriod(period: Period) {
        FdbStorageImpl.updatePeriod(periodToPeriodD(period))
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

    private fun periodDToPeriod(periodD: PeriodD?): Period {

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

    private fun periodDToPeriodArray(arrayPeriodsD: Array<PeriodD>): ArrayList<Period> {
        val periods = ArrayList<Period>()
        for (e in arrayPeriodsD) {
            periods.add(periodDToPeriod(e))
        }
        return periods
    }
    /********* ! mappers  ************/

}
