package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mordansoft.homebank.domain.model.Profit


@Entity(tableName = "profit")
data class ProfitD(
    @PrimaryKey(autoGenerate = false)
    var id         : Long    = Profit.DEFAULT_ID,
    var name       : String  = Profit.DEFAULT_NAME,
    var description: String  = Profit.DEFAULT_DESCRIPTION,
    var amount     : Float   = Profit.DEFAULT_AMOUNT,
    var periodId   : Int     = Profit.DEFAULT_PERIOD_ID,
    var statusId   : Int     = Profit.DEFAULT_STATUS_ID,
    var date       : Long    = Profit.DEFAULT_DATE,
    var timestamp  : Long    = Profit.DEFAULT_TIMESTAMP,
    var repeater   : Boolean = Profit.DEFAULT_REPEATER

)
