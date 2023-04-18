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

){
    companion object{

        /*********** mappers  ************/
        fun profitToProfitD(profit: Profit): ProfitD {
            return ProfitD(
                id          = profit.id         ,
                name        = profit.name       ,
                description = profit.description,
                amount      = profit.amount     ,
                periodId    = profit.periodId   ,
                statusId    = profit.statusId     ,
                date        = profit.date       ,
                timestamp   = profit.timestamp  ,
                repeater    = profit.repeater
            )
        }

        fun profitDToProfit(profitD: ProfitD): Profit {
            return Profit(
                id          = profitD.id         ,
                name        = profitD.name       ,
                description = profitD.description,
                amount      = profitD.amount     ,
                periodId    = profitD.periodId   ,
                statusId    = profitD.statusId     ,
                date        = profitD.date       ,
                timestamp   = profitD.timestamp  ,
                repeater    = profitD.repeater

            )
        }

        fun profitDToProfitArray(arrayProfitsD: Array<ProfitD>): ArrayList<Profit> {
            val profits = ArrayList<Profit>()
            for (e in arrayProfitsD) {
                profits.add(profitDToProfit(e))
            }
            return profits
        }
        /********* ! mappers  ************/
    }
}
