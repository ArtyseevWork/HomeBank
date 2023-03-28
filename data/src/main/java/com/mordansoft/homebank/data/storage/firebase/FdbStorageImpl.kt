package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mordansoft.homebank.data.model.PeriodD
import com.mordansoft.homebank.data.model.PreferencesD
import com.mordansoft.homebank.data.model.ProfitD
import com.mordansoft.homebank.data.model.PurchaseD

object FdbStorageImpl{

    private const val FDB_URL: String = "https://homebank-8-default-rtdb.europe-west1.firebasedatabase.app/"

    enum class Folders { Profit, Purchase, Period, Preferences }

    fun getReference(folders: Folders): DatabaseReference{

            val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(FDB_URL)
            val dbReference: DatabaseReference =
                firebaseDatabase.getReference(getUserId()!!).child(folders.name)
            return dbReference

    }
    
    fun updateProfit(profit : ProfitD){
        val userId : String? =  getUserId()
        if (userId != null){
            val dbProfitReference =  getReference(Folders.Profit)
            dbProfitReference.child(profit.id.toString()).setValue(profit)
        }
    }
    fun updatePeriod(period : PeriodD){
        val userId : String? =  getUserId()
        if (userId != null){
            val dbPeriodReference =  getReference(Folders.Period)
            dbPeriodReference.child(period.id.toString()).setValue(period)
        }
    }
    
    fun updatePurchase(purchase : PurchaseD){
        val userId : String? =  getUserId()
        if (userId != null){
            val dbPurchaseReference =  getReference(Folders.Purchase)
            dbPurchaseReference.child(purchase.id.toString()).setValue(purchase)
        }
    }
    
    fun updatePreferences(preferences : PreferencesD){
        val userId : String? =  getUserId()
        if (userId != null){
            val dbPreferencesReference =  getReference(Folders.Preferences)
            dbPreferencesReference.setValue(preferences)
        }
    }

    fun getUserId(): String? {
        return try {
            val mAuth = FirebaseAuth.getInstance()
            val user = mAuth.currentUser
            user!!.uid
        } catch (e: Exception) {
            null
        }
    }
}

