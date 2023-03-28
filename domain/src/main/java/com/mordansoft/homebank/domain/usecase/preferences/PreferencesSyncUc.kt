package com.mordansoft.homebank.domain.usecase.preferences

import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.repo.PreferencesRepo

class PreferencesSyncUc (private val preferencesRepo: PreferencesRepo) {

    suspend fun execute(preferencesFdb: Preferences) {
        val preferencesSql: Preferences = preferencesRepo.getPreferences()

        if (preferencesSql.timestamp > preferencesFdb.timestamp) {
            preferencesRepo.updateRemotePreferences(preferencesSql)
        } else if (preferencesSql.timestamp < preferencesFdb.timestamp) {
            preferencesRepo.updatePreferences(preferencesFdb)
        }
    }
}