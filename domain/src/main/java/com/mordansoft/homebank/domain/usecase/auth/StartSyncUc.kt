package com.mordansoft.homebank.domain.usecase.auth

import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.*

class StartSyncUc(private val periodRepo      : PeriodRepo,
                  private val preferencesRepo : PreferencesRepo,
                  private val purchaseRepo    : PurchaseRepo,
                  private val profitRepo      : ProfitRepo,
                  private val remoteRepo      : RemoteRepo
) {

    fun start() : Boolean{
        var result : Boolean = false

        if (remoteRepo.getUserId()!= null) {
            profitRepo.updateRemoteProfit(Profit())
            remoteRepo.getAllData(this)

            result = true
        }
        return result
    }

    suspend fun step2(profitIdListFdb  : ArrayList<Long>,
                      purchaseIdListFdb: ArrayList<Long>,
                      periodIdListFdb  : ArrayList<Int>,
                      preferencesFdb   : Preferences? = null
    ){
        val sqlPreferences = preferencesRepo.getPreferences()

        if (preferencesFdb == Preferences()){
            preferencesRepo.updateRemotePreferences(sqlPreferences)
        }

        val periodIdListSql   = periodRepo
            .getAllId()
            .filter {  e -> !periodIdListFdb.contains(e) }

        val profitIdListSql   = profitRepo
            .getAllId()
            .filter {  e -> !profitIdListFdb.contains(e) }

        val purchaseIdListSql = purchaseRepo
            .getAllId()
            .filter {  e -> !purchaseIdListFdb.contains(e) }


        for( e in  periodIdListSql){
            periodRepo.updateRemotePeriod(periodRepo.getPeriodById(e))
        }

        for( e in  profitIdListSql){
            profitRepo.updateRemoteProfit(profitRepo.getProfitById(e))
        }

        for( e in  purchaseIdListSql){
            purchaseRepo.updateRemotePurchase(purchaseRepo.getPurchaseById(e))
        }

    }

}