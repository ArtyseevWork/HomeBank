package com.mordansoft.homebank.data.repo

import com.mordansoft.homebank.data.model.ProfitD
import com.mordansoft.homebank.data.model.ProfitD.Companion.profitDToProfit
import com.mordansoft.homebank.data.model.ProfitD.Companion.profitDToProfitArray
import com.mordansoft.homebank.data.model.ProfitD.Companion.profitToProfitD
import com.mordansoft.homebank.data.storage.ProfitDao
import com.mordansoft.homebank.data.storage.firebase.FdbStorageImpl
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo
import kotlinx.coroutines.*

class ProfitRepoImpl (private val profitDao: ProfitDao,
                      private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ProfitRepo {

    override suspend fun updateProfit(profit: Profit) {
        profitDao.updateProfit(profitToProfitD(profit))

    }

    override suspend fun getMainProfits(periodId: Int): ArrayList<Profit> {
        var resultList: ArrayList<Profit>
        withContext(defaultDispatcher) {
            resultList = profitDToProfitArray(profitDao.getMainProfits(periodId = periodId))
        }
        return resultList
    }

    override suspend fun getAllProfits(): ArrayList<Profit> {
        var resultList: ArrayList<Profit>
        withContext(defaultDispatcher) {
            resultList = profitDToProfitArray(profitDao.getAllProfits())
        }
        return resultList
    }

    override suspend fun getAllId(): Array<Long> {
        return profitDao.getAllId()
    }

    override suspend fun getProfitById(profitId: Long): Profit {

        return profitDToProfit(profitDao.getProfitById(profitId));
    }


    override suspend fun insertTestProfit(){
        /*for ( i in 1..10){
            try{
                profitDao.insertAll(ProfitD(id = i.toLong()))
            } catch (e: Exception){
                println("$i - id already exist")
            }
        }*/
    }

    override suspend fun insertProfit(profit: Profit) {
        profitDao.insertAll(profitToProfitD(profit))
        updateRemoteProfit(profit)

    }


    override fun updateRemoteProfit(profit: Profit) {
        val userId : String? = FdbStorageImpl.getUserId()
        if (userId != null){
            val dbProfitReference = FdbStorageImpl.getReference(FdbStorageImpl.Folders.Profit)
            dbProfitReference
                .child(profit.id.toString())
                .setValue(profitToProfitD(profit))
        }
    }



}
