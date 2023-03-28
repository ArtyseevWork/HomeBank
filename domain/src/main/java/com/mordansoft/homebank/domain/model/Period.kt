package com.mordansoft.homebank.domain.model

data class Period(
    val id              : Int    = DEFAULT_ID,
    var name            : String = DEFAULT_NAME,
    var dateFrom        : Long   = DEFAULT_DATE_FROM,
    var timestamp       : Long   = DEFAULT_TIMESTAMP,
    var dateTo          : Long   = DEFAULT_DATE_TO,
    var statusId        : Int    = DEFAULT_STATUS_ID,
    var nextPeriodId    : Int    = DEFAULT_NEXT_PERIOD_ID,
    var previousPeriodId: Int    = DEFAULT_PREVIOUS_PERIOD_ID,

    ) {

    companion object {
        const val DEFAULT_ID                : Int    = 0
        const val DEFAULT_NAME              : String = "Period"
        const val DEFAULT_DATE_FROM         : Long   = 0
        const val DEFAULT_DATE_TO           : Long   = 100000
        const val DEFAULT_STATUS_ID         : Int    =  0
        const val DEFAULT_NEXT_PERIOD_ID    : Int    =  0
        const val DEFAULT_PREVIOUS_PERIOD_ID: Int    =  0
        const val ONE_DAY_MILLIS            : Long   = 86400
        const val DEFAULT_TIMESTAMP         : Long = 0L

        fun getPeriodName(): String {
            return "ExamplePeriod"
        }
    }
}
