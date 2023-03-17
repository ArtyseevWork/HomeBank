package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class SetPurchaseUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchase: Purchase){
        purchaseRepo.updatePurchase(purchase)
    }


    /*
     if (sumOfChildrenSpending - purchase.price > 0.01) {
             purchase.price = (sumOfChildrenSpending)
             Purchase.updatePurchase(this, purchase, false)
             val builder = AlertDialog.Builder(this)
             builder.setTitle(getString(R.string.budgetIncreased))
                 .setMessage(getString(R.string.budgetIncreasedMessage)) //.setIcon(R.drawable.ic_launcher_cat)
                 .setPositiveButton(
                     getString(R.string.ok)
                 ) { dialog, id -> // Закрываем окно
                     dialog.cancel()
                 }
             builder.show()
         }

     */
}