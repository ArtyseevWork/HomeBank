package com.mordansoft.homebank.data.storage.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mordansoft.homebank.data.model.PreferencesD
import com.mordansoft.homebank.data.model.PreferencesD.Companion.preferencesDToPreferences
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.usecase.preferences.PreferencesSyncUc
import kotlinx.coroutines.*

class ChildPreferencesListener (
    private val preferencesSyncUc : PreferencesSyncUc,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
        val preferences = snapshot.getValue(PreferencesD::class.java)
        if (preferences != null) {
            syncPreferences(preferences)
        }
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

    private fun syncPreferences(preferences : PreferencesD){
        runBlocking{launch{
            preferencesSyncUc.execute(preferencesDToPreferences(preferences))
        }}
    }

}
