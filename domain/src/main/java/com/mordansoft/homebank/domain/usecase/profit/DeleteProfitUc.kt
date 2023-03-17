package com.mordansoft.homebank.domain.usecase.profit

import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.ProfitRepo
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class DeleteProfitUc(private val profitRepo: ProfitRepo) {

    suspend fun execute(profit: Profit){
        //todo
    }
}