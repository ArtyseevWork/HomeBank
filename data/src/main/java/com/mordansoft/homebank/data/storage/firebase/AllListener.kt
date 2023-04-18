package com.mordansoft.homebank.data.storage.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.auth.StartSyncUc
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AllListener(private val startSyncUc: StartSyncUc) : OnCompleteListener<DataSnapshot> {

    override fun onComplete(snapshot2: Task<DataSnapshot>) {
        var snapshot = snapshot2.result
        
        val profitArrayList:   ArrayList<Profit>   = ArrayList()
        val periodArrayList:   ArrayList<Period>   = ArrayList()
        val purchaseArrayList: ArrayList<Purchase> = ArrayList()

        val profitIdList:   ArrayList<Long>   = ArrayList()
        val purchaseIdList: ArrayList<Long> = ArrayList()
        val periodIdList:   ArrayList<Int>   = ArrayList()


        var preferences : Preferences = Preferences()
        
        val profitSnapshot = snapshot.child(FdbStorageImpl.Folders.Profit.name).getChildren()
        val purchaseSnapshot = snapshot.child(FdbStorageImpl.Folders.Purchase.name).getChildren()
        val periodSnapshot = snapshot.child(FdbStorageImpl.Folders.Period.name).getChildren()
        val prefSnapshot = snapshot.child(FdbStorageImpl.Folders.Preferences.name)
        for (child in profitSnapshot) {
            val profit = child.getValue(Profit::class.java)
            val profitId = child.key?.toLongOrNull()
            if (profit != null && profitId != null) {
                profitArrayList.add(profit)
                profitIdList.add(profitId)
            }
        }

        for (child in purchaseSnapshot) {
            val purchase = child.getValue(Purchase::class.java)
            val purchaseId = child.key?.toLongOrNull()
            if (purchase != null && purchaseId != null) {
                purchaseArrayList.add(purchase)
                purchaseIdList.add(purchaseId)
            }
        }

        for (child in periodSnapshot) {
            val period = child.getValue(Period::class.java)
            val periodId = child.key?.toIntOrNull()
            if (period != null && periodId != null) {
                periodArrayList.add(period)
                periodIdList.add(periodId)
            }
        }

        val pref = prefSnapshot.getValue(Preferences::class.java)
        if (pref != null ) {
            preferences = pref
        }


        runBlocking {launch{
            startSyncUc.step2(
                profitIdListFdb   = profitIdList,
                purchaseIdListFdb = purchaseIdList,
                periodIdListFdb   = periodIdList,
                preferencesFdb    = preferences
            )
        }}

    }
}