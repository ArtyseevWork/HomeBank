package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mordansoft.homebank.domain.model.Period

@Entity(tableName = "period")
data class PeriodD(
    @PrimaryKey(autoGenerate = false)
    val id       : Int    = Period.DEFAULT_ID,
    val name     : String = Period.DEFAULT_NAME,
    val dateFrom : Long   = Period.DEFAULT_DATE_FROM,
    val dateTo   : Long   = Period.DEFAULT_DATE_TO,
    val statusId : Int    = Period.DEFAULT_STATUS_ID)
