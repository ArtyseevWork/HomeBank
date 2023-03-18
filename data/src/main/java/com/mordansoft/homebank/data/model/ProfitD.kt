package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profit")
data class ProfitD(
    @PrimaryKey(autoGenerate = false)
    var id         : Long = -8L,
    var idFdb      : Long = -8L,
    var name       : String = "NewProfit",
    var description: String = "Your Description",
    var amount     : Float= 1000f,
    var periodId   : Int = -8,
    var statusId   : Int = 300,
    var date       : Long = 0L,
    var timestamp  : Long = 0L,
    var repeater   : Boolean = false

)
