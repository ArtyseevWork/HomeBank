package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.usecase.profit.ProfitSyncUc
import kotlinx.coroutines.*

class ChildProfitListener (
    private val profitSyncUc : ProfitSyncUc,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ChildEventListener  {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?,) {
        val profit = snapshot.getValue(Profit::class.java)
        if (profit != null) {
            syncProfit(profit)
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val profit = snapshot.getValue(Profit::class.java)
        if (profit != null) {
            syncProfit(profit)
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

    private fun syncProfit(profit : Profit){


        GlobalScope.launch{
            profitSyncUc.execute(profit)
        }
    }

}
