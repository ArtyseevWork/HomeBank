package com.mordansoft.homebank.data.model

data class PreferencesD(
    var activePeriod: Int = 1,
    var timestamp: Long = 0,
    var profitsMode: Int = 0,  //1 - planing mode (profits from previous period), 0 - current mode (profits from current period): Int = 0
    var onlineMode: Boolean = false)
