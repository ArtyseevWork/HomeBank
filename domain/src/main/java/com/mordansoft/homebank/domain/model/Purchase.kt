package com.mordansoft.homebank.domain.model

data class Purchase(
    var id          : Long     = DEFAULT_ID         ,
    var name        : String   = DEFAULT_NAME       ,
    var description : String   = DEFAULT_DESCRIPTION,
    var price       : Float    = DEFAULT_PRICE      ,
    var count       : Float    = DEFAULT_COUNT      ,
    var periodId    : Int      = DEFAULT_PERIOD_ID  ,
    var statusId    : Int      = DEFAULT_STATUS_ID  ,
    var parentId    : Long     = DEFAULT_PARENT_ID  ,
    var repeater    : Boolean  = DEFAULT_REPEATER   ,
    var timestamp   : Long     = DEFAULT_TIMESTAMP  )
{
    companion object {
        const val DEFAULT_ID         : Long    = 0
        const val DEFAULT_NAME       : String  = "New Purchase"
        const val DEFAULT_DESCRIPTION: String  = "Your description"
        const val DEFAULT_PRICE      : Float   = 100f
        const val DEFAULT_COUNT      : Float   = 1f
        const val DEFAULT_PERIOD_ID  : Int     = 1
        const val DEFAULT_STATUS_ID  : Int     = 100
        const val DEFAULT_PARENT_ID  : Long    = 0
        const val DEFAULT_REPEATER   : Boolean = false
        const val DEFAULT_TIMESTAMP  : Long    = 0
        const val DEFAULT_PARENT     : Long    = 0
        const val PARENT_MAIN        : Long    = 0
        const val STATUS_PLANNED     : Int     = 100
        const val STATUS_DELAYED     : Int     = 200
        const val STATUS_PURCHASED   : Int     = 300
        const val STATUS_REMOVED     : Int     = 400
    }
}
