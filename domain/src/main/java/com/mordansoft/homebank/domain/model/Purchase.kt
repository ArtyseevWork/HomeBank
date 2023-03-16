package com.mordansoft.homebank.domain.model

data class Purchase(

    val id: Long = -8,
    val idFdb: Long,
    val name: String = "New Purchase",
    val description: String = "Your description",
    val price: Float = 100f,
    val count: Float = 1f,
    val periodId: Int = -8,
    val statusId: Int  = -8,
    val parentId: Long = -8,
    val repeater: Boolean  = false,
    val timestamp: Long = 0)
