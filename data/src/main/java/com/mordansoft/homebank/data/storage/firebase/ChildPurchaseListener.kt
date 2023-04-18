package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.mordansoft.homebank.data.model.PurchaseD
import com.mordansoft.homebank.data.model.PurchaseD.Companion.purchaseDToPurchase
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.purchase.PurchaseSyncUc
import kotlinx.coroutines.*

class ChildPurchaseListener (
    private val purchaseSyncUc : PurchaseSyncUc,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ChildEventListener  {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?,) {
        val purchase = snapshot.getValue(PurchaseD::class.java)
        if (purchase != null) {
            syncPurchase(purchase)
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val purchase = snapshot.getValue(PurchaseD::class.java)
        if (purchase != null) {
            syncPurchase(purchase)
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

    private fun syncPurchase(purchase : PurchaseD){
        runBlocking{launch{
            purchaseSyncUc.execute(purchaseDToPurchase(purchase))
        }}
    }

}
