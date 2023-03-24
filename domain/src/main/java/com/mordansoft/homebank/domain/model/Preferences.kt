package com.mordansoft.homebank.domain.model

data class Preferences(
    var activePeriod   : Int      = DEFAULT_ACTIVE_PERIOD,
    var timestamp      : Long     = DEFAULT_TIMESTAMP    ,
    var profitsMode    : Int      = DEFAULT_PROFITS_MODE ,
    var onlineMode     : Boolean  = DEFAULT_ONLINE_MODE  ,
    val mainCurrencyId : Int      = Currency.UAH_ISO_4217
)
{
    companion object{
        const val DEFAULT_ACTIVE_PERIOD: Int     = 1
        const val DEFAULT_TIMESTAMP    : Long    = 0
        const val DEFAULT_PROFITS_MODE : Int     = 0
        const val DEFAULT_ONLINE_MODE  : Boolean = false
        const val PLANING_MODE         : Int     = 1// planing mode (profits from previous period)
        const val CURRENT_MODE         : Int     = 0// current mode (profits from current period)
    }
}
