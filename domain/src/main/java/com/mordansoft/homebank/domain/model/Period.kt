package com.mordansoft.homebank.domain.model

data class Period(
    val id: Int = 0,
    var name: String = "Period",
    var dateFrom: Long = 0,
    var dateTo: Long = 100000,
    var statusId: Int = 0,
    var nextPeriodId: Int = 0,
    var previousPeriodId: Int = 0) {

    companion object {
        fun getPeriodName(): String {
            return "ExamplePeriod"
        }
    }
}
