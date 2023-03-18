package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Preferences

interface PreferencesRepo {
    suspend fun updatePreferences(preferences: Preferences)
    suspend fun getPreferences(): Preferences
}