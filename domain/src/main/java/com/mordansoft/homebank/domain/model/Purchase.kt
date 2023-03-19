package com.mordansoft.homebank.domain.model

data class Purchase(

    var id: Long = -8,
    var idFdb: Long,
    var name: String = "New Purchase",
    var description: String = "Your description",
    var price: Float = 100f,
    var count: Float = 1f,
    var periodId: Int = 1,
    var statusId: Int  = 100,
    var parentId: Long = 0,
    var repeater: Boolean  = false,
    var timestamp: Long = 0)
