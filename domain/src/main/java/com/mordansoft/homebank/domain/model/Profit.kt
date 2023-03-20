package com.mordansoft.homebank.domain.model

data class Profit(
    var id         : Long = 0,
    var idFdb      : Long = 0,
    var name       : String = "NewProfit",
    var description: String = "Your Description",
    var amount     : Float= 0f,
    var periodId   : Int = -8,
    var statusId   : Int = -8,
    var date       : Long = 0L,
    var timestamp  : Long = 0L,
    var repeater   : Boolean = false

)
