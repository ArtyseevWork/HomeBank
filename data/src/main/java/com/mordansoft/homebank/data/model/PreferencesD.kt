package com.mordansoft.homebank.data.model
import com.mordansoft.homebank.domain.model.Currency
import com.mordansoft.homebank.domain.model.Preferences

data class PreferencesD(
    var activePeriod   : Int     = Preferences.DEFAULT_ACTIVE_PERIOD,
    var timestamp      : Long    = Preferences.DEFAULT_TIMESTAMP    ,
    var profitsMode    : Int     = Preferences.DEFAULT_PROFITS_MODE ,
    var onlineMode     : Boolean = Preferences.DEFAULT_ONLINE_MODE  ,
    val mainCurrencyId : Int     = Currency.UAH_ISO_4217){
    companion object{
        /*********** mappers  ************/
        fun preferencesToPreferencesD(preferences: Preferences): PreferencesD {
            return PreferencesD(
                activePeriod = preferences.activePeriod,
                timestamp    = preferences.timestamp   ,
                profitsMode  = preferences.profitsMode ,
                onlineMode   = preferences.onlineMode
            )
        }

        fun preferencesDToPreferences(preferencesD: PreferencesD): Preferences {
            return Preferences(
                activePeriod = preferencesD.activePeriod,
                timestamp    = preferencesD.timestamp   ,
                profitsMode  = preferencesD.profitsMode ,
                onlineMode   = preferencesD.onlineMode
            )
        }

        /********* ! mappers  ************/
    }
}

