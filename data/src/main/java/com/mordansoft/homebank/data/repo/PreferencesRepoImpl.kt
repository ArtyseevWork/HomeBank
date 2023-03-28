package com.mordansoft.homebank.data.repo

import com.mordansoft.homebank.data.model.PreferencesD
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
        FdbStorageImpl.updatePreferences(preferencesToPreferencesD(preferences))
    }

    /*********** mappers  ************/
    private fun preferencesToPreferencesD(preferences: Preferences): PreferencesD {
        return PreferencesD(
            activePeriod = preferences.activePeriod,
            timestamp    = preferences.timestamp   ,
            profitsMode  = preferences.profitsMode ,
            onlineMode   = preferences.onlineMode
        )
    }

    private fun preferencesDToPreferences(preferencesD: PreferencesD): Preferences {
        return Preferences(
            activePeriod = preferencesD.activePeriod,
            timestamp    = preferencesD.timestamp   ,
            profitsMode  = preferencesD.profitsMode ,
            onlineMode   = preferencesD.onlineMode
        )
    }

    /********* ! mappers  ************/

}
