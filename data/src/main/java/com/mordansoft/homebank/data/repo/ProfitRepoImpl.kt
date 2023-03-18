package com.mordansoft.homebank.data.repo


import androidx.annotation.WorkerThread
import com.mordansoft.homebank.data.model.ProfitD
import com.mordansoft.homebank.data.storage.ProfitDao
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo
import kotlinx.coroutines.*

class ProfitRepoImpl (private val profitDao: ProfitDao,
                      private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ProfitRepo {

    override suspend fun updateProfit(profit: Profit) {
        return profitDao.updateProfit(profitToProfitD(profit))
    }

    override suspend fun getMainProfits(periodId: Int): ArrayList<Profit> {
        var resultList: ArrayList<Profit>
        withContext(defaultDispatcher) {
            resultList = profitDToProfitArray(profitDao.getMainProfits(periodId = periodId))
        }
        return resultList
    }

    override suspend fun getProfitById(profitId: Long): Profit {
        return profitDToProfit(profitDao.getProfitById(profitId));
    }


    override suspend fun insertTestProfit(){
        for ( i in 1..10){
            try{
                profitDao.insertAll(ProfitD(id = i.toLong()))
            } catch (e: Exception){
                println("$i - id already exist")
            }
        }
    }

    /*********** mappers  ************/
    private fun profitToProfitD(profit: Profit): ProfitD {
        return ProfitD(
            id          = profit.id         ,
            idFdb       = profit.idFdb      ,
            name        = profit.name       ,
            description = profit.description,
            amount      = profit.amount     ,
            periodId    = profit.periodId   ,
            statusId    = profit.statusId     ,
            date        = profit.date       ,
            timestamp   = profit.timestamp  ,
            repeater    = profit.repeater
        )
    }

    private fun profitDToProfit(profitD: ProfitD): Profit {
        return Profit(
            id          = profitD.id         ,
            idFdb       = profitD.idFdb      ,
            name        = profitD.name       ,
            description = profitD.description,
            amount      = profitD.amount     ,
            periodId    = profitD.periodId   ,
            statusId    = profitD.statusId     ,
            date        = profitD.date       ,
            timestamp   = profitD.timestamp  ,
            repeater    = profitD.repeater

        )
    }

    private fun profitDToProfitArray(arrayProfitsD: Array<ProfitD>): ArrayList<Profit> {
        val profits = ArrayList<Profit>()
        for (e in arrayProfitsD) {
            profits.add(profitDToProfit(e))
        }
        return profits
    }
    /********* ! mappers  ************/

}
