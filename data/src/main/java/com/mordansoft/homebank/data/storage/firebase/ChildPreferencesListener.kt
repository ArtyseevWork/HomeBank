package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.usecase.preferences.PreferencesSyncUc
import kotlinx.coroutines.*

class ChildPreferencesListener (
    private val preferencesSyncUc : PreferencesSyncUc,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ChildEventListener  {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?,) {
        val preferences = snapshot.getValue(Preferences::class.java)
        if (preferences != null) {
            syncPreferences(preferences)
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val preferences = snapshot.getValue(Preferences::class.java)
        if (preferences != null) {
            syncPreferences(preferences)
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

    private fun syncPreferences(preferences : Preferences){


        GlobalScope.launch{
            preferencesSyncUc.execute(preferences)
        }
    }

}
