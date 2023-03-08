package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase")
data class PurchaseD(
    @PrimaryKey
    val id: Long = 0,
    val idFdb: String  = "-1",
    val name: String  = "New Purchase",
    val description: String = "Your Description" ,
    val price: Float = 10.toFloat(),
    val count: Float = 1.toFloat(),
    val period: Int = 0,
    val status: Int = 0,
    val parentId: String = "-1",
    val repeater: Boolean = false,
    val timestamp: Long = 0)
