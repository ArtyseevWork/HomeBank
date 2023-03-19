package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.repo.ProfitRepo

class SetProfitUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(profit: Profit){
        profitRepo.updateProfit(profit)
    }

    /*
    var unixTime = try {
                val date = dateFormat.parse(binding.profitDate.text.toString()) as Date
                date.time / 1000
            } catch (e: ParseException) {
                val toast = Toast.makeText(view.context, "Неверный тип даты", Toast.LENGTH_LONG)
                toast.show()
                return
            }
    * */
    
}