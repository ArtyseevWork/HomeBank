package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.usecase.period.PeriodSyncUc
import kotlinx.coroutines.*

class ChildPeriodListener (
    private val periodSyncUc : PeriodSyncUc,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ChildEventListener  {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?,) {
        val period = snapshot.getValue(Period::class.java)
        if (period != null) {
            syncPeriod(period)
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val period = snapshot.getValue(Period::class.java)
        if (period != null) {
            syncPeriod(period)
        }
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        TODO("Not yet implemented")
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        TODO("Not yet implemented")
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

    private fun syncPeriod(period : Period){


        GlobalScope.launch{
            periodSyncUc.execute(period)
        }
    }

}
