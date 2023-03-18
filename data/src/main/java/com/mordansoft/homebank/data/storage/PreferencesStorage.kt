package com.mordansoft.homebank.data.storage

import com.mordansoft.homebank.domain.model.Preferences

interface PreferencesStorage {
    suspend fun updatePreferences(preferences: Preferences)
    suspend fun getPreferences(): Preferences
}