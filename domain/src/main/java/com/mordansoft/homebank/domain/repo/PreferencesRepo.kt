package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.model.Profit

interface PreferencesRepo {
    suspend fun updatePreferences(preferences: Preferences)
    suspend fun getPreferences(): Preferences
    suspend fun updateRemotePreferences(preferences: Preferences)
}