package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profit")
data class ProfitD(
    @PrimaryKey(autoGenerate = false)
    var id         : Long    = 0,
    var idFdb      : Long    = 0,
    var name       : String  = "New Profit",
    var description: String  = "Your Description",
    var amount     : Float   = 1000f,
    var periodId   : Int     = 1,
    var statusId   : Int     = 100,
    var date       : Long    = 0L,
    var timestamp  : Long    = 0L,
    var repeater   : Boolean = false

)
