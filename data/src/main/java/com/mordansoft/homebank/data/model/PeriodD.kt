package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "period")
data class PeriodD(
    @PrimaryKey(autoGenerate = false)
    val id       : Int = 0,
    val name     : String = "Period",
    val dateFrom : Long = 0,
    val dateTo   : Long = 100000,
    val statusId : Int = 0)
