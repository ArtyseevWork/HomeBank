package com.mordansoft.homebank.data.storage

import com.mordansoft.homebank.data.model.PreferencesD

interface PreferencesStorage {
    suspend fun updatePreferences(preferences: PreferencesD)
    suspend fun getPreferences(): PreferencesD
}