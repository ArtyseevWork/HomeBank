package com.mordansoft.homebank.domain.model

data class Period(
   val id: Int = 0,
   val name: String = "January 2023",
   val dateFrom: Long = 0,
   val dateTo: Long = 100000,
   val statusId: Int = 0) {

    companion object {
        fun getPeriodName(): String {
            return "ExamplePeriod"
        }
    }
}
