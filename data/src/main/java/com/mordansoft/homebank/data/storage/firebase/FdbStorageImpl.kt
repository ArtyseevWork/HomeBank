package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mordansoft.homebank.domain.repo.RemoteRepo
import com.mordansoft.homebank.domain.usecase.auth.StartSyncUc

object FdbStorageImpl : RemoteRepo{

    const val FDB_URL: String = "https://homebank-8-default-rtdb.europe-west1.firebasedatabase.app/"

    enum class Folders { Profit, Purchase, Period, Preferences }

    fun getReference(folders: Folders): DatabaseReference{

            val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(FDB_URL)
            val dbReference: DatabaseReference =
                firebaseDatabase.getReference(getUserId()!!).child(folders.name)
            return dbReference

    }

    override fun getUserId(): String? {
        return try {
            val mAuth = FirebaseAuth.getInstance()
            val user = mAuth.currentUser
            user!!.uid
        } catch (e: Exception) {
            null
        }
    }

    override fun getAllData(startSyncUc: StartSyncUc) {
        val userId: String? = getUserId()
        if (userId != null) {
            println("!!!! 130 !!!!")
            val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(FdbStorageImpl.FDB_URL)
            val dbReferenceAll: DatabaseReference = firebaseDatabase.getReference(getUserId()!!)
            dbReferenceAll.get().addOnCompleteListener(AllListener(startSyncUc))
            println("!!!! 149 !!!!")
        }
    }

}

