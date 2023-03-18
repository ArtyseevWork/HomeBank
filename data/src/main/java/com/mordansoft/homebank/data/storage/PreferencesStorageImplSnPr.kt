package com.mordansoft.homebank.data.storage

import android.content.Context
import android.preference.PreferenceManager
import com.mordansoft.homebank.domain.model.Preferences


class PreferencesStorageImplSnPr(val context: Context) : PreferencesStorage {

    override suspend fun updatePreferences(preferences: Preferences) {
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val myEditor = myPreferences.edit()
        myEditor.putInt("ACTIVE_PERIOD", preferences.activePeriod)
        myEditor.putInt("PROFIT_MODE", preferences.profitsMode)
        myEditor.putLong("TIMESTAMP", preferences.timestamp)
        myEditor.putBoolean("ONLINE_MODE", preferences.onlineMode)
        myEditor.apply()
    }

    override suspend fun getPreferences(): Preferences {
        val activePeriod: Int
        val timestamp: Long
        val profitMode: Int
        val onlineMode: Boolean
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        activePeriod = myPreferences.getInt("ACTIVE_PERIOD", 0)
        timestamp = myPreferences.getLong("TIMESTAMP", 0)
        profitMode = myPreferences.getInt("PROFIT_MODE", 0)
        onlineMode = myPreferences.getBoolean("ONLINE_MODE", false)
        return Preferences(activePeriod, timestamp, profitMode, onlineMode)
    }
}