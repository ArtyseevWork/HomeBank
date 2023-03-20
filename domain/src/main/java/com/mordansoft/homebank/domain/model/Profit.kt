package com.mordansoft.homebank.domain.model

data class Profit(
    var id         : Long    = DEFAULT_ID         ,
    var name       : String  = DEFAULT_NAME       ,
    var description: String  = DEFAULT_DESCRIPTION,
    var amount     : Float   = DEFAULT_AMOUNT     ,
    var periodId   : Int     = DEFAULT_PERIOD_ID  ,
    var statusId   : Int     = DEFAULT_STATUS_ID  ,
    var date       : Long    = DEFAULT_DATE       ,
    var timestamp  : Long    = DEFAULT_TIMESTAMP  ,
    var repeater   : Boolean = DEFAULT_REPEATER
){
    companion object {
        const val DEFAULT_ID         : Long = 0
        const val DEFAULT_NAME       : String = "New Profit"
        const val DEFAULT_DESCRIPTION: String = "Your Description"
        const val DEFAULT_AMOUNT     : Float = 0f
        const val DEFAULT_PERIOD_ID  : Int = -8
        const val DEFAULT_STATUS_ID  : Int = -8
        const val DEFAULT_DATE       : Long = 0L
        const val DEFAULT_TIMESTAMP  : Long = 0L
        const val DEFAULT_REPEATER   : Boolean = false
        const val STATUS_PLANNED     : Int = 100
        const val STATUS_ACCRUED     : Int = 200
        const val STATUS_RECEIVED    : Int = 300
        const val STATUS_REMOVED     : Int = 400
    }

}