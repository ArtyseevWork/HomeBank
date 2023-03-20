package com.mordansoft.homebank.domain.usecase.purchase

import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.repo.PurchaseRepo

class SetPurchaseUc(private val purchaseRepo: PurchaseRepo) {

    suspend fun execute(purchase: Purchase){
        var timestamp: Long = System.currentTimeMillis()
            purchase.timestamp = timestamp
        if (purchase.id != Purchase.DEFAULT_ID){   //not new purchase
            purchaseRepo.updatePurchase(purchase)
        } else { // new purchase
            purchase.id = timestamp
            purchaseRepo.insertPurchase(purchase)
        }

    }


    /*
    if (sumOfChildrenSpending - purchaseEditable.price > 0.01) {
                 binding.profitAmount.setText(sumOfChildrenSpending.toString())
                 purchaseEditable.price = sumOfChildrenSpending
                 val builder = AlertDialog.Builder(this)
                 builder.setTitle(getString(R.string.budgetIncreased))
                     .setMessage(getString(R.string.budgetIncreasedMessage)) //.setIcon(R.drawable.ic_launcher_cat)
                     .setPositiveButton(
                         getString(R.string.ok)
                     ) { dialog, id -> // Закрываем окно
                         dialog.cancel()
                     }
                 builder.show()
             } else {
                 /*if (newPurchase) {
                     Purchase.insertPurchase(this, purchaseEditable)
                 } else {
                     Purchase.updatePurchase(this, purchaseEditable, false)
                 }*/
                 /* if(periodIdAtStart != purchaseEditable.getPeriodId()){
                     Purchase.setPeriodForChildren(this,purchaseEditable.getIdFDB(), purchaseEditable.getPeriodId(), periodIdAtStart);
                 }*/
                 val intent: Intent
                 if (parentId != -8L) {
                     intent = Intent(this, PurchaseActivity::class.java)
                     intent.putExtra("EXTRA_PURCHASE_ID", parentId)
                 }

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