package com.mordansoft.homebank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "purchase")
data class PurchaseD(
    @PrimaryKey (autoGenerate = false)
    val id: Long = -8,
    val idFdb: Long = -8,
    val name: String = "New Purchase",
    val description: String = "Your description",
    val price: Float = 100f,
    val count: Float = 1f,
    val periodId: Int = 1,
    val statusId: Int  = -8,
    val parentId: Long = -8,
    val repeater: Boolean  = false,
    val timestamp: Long = 0)
