package com.mordansoft.homebank.data.repo

import com.mordansoft.homebank.data.model.PreferencesD
import com.mordansoft.homebank.data.model.PreferencesD.Companion.preferencesDToPreferences
import com.mordansoft.homebank.data.model.PreferencesD.Companion.preferencesToPreferencesD
import com.mordansoft.homebank.data.storage.PreferencesStorageImplSnPr
import com.mordansoft.homebank.data.storage.firebase.FdbStorageImpl
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.repo.PreferencesRepo

import kotlinx.coroutines.*

class PreferencesRepoImpl (private val preferencesStorageImplSnPr: PreferencesStorageImplSnPr,
                           private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) :
    PreferencesRepo {

    override suspend fun updatePreferences(preferences: Preferences) {
        preferencesStorageImplSnPr.updatePreferences(preferencesToPreferencesD(preferences))
    }

    override suspend fun getPreferences(): Preferences {
        return preferencesDToPreferences(preferencesStorageImplSnPr.getPreferences())
    }

    override suspend fun updateRemotePreferences(preferences: Preferences) {
        val userId : String? = FdbStorageImpl.getUserId()
        if (userId != null){
            val dbPreferencesReference =
                FdbStorageImpl.getReference(FdbStorageImpl.Folders.Preferences)
            dbPreferencesReference.setValue(preferencesToPreferencesD(preferences))
        }
    }



}
