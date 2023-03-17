package com.mordansoft.homebank.ui.purchase

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.databinding.ActivityPurchaseBinding
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.ui.StubActivity
import com.mordansoft.homebank.ui.main.MainActivity
import com.mordansoft.homebank.ui.main.PurchaseAdapter

class PurchaseActivity : AppCompatActivity() {
    //private int periodId;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PurchaseAdapter
    lateinit var purchase: Purchase
    private var listPurchases: ArrayList<Purchase> = ArrayList()
    private var closePeriodMode = false
    private var purchaseId: Long = 0
    private var parentId: Long = 0
    lateinit var mMyApp: App
    private lateinit var vm : PurchaseViewModel
    private var sumOfChildrenSpending: Float = 0F

    @javax.inject.Inject
    lateinit var vmFactory: PurchaseViewModelFactory


    private val binding by lazy {
        ActivityPurchaseBinding.inflate(layoutInflater);
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMyApp = this.applicationContext as App
        (applicationContext as App).appComponent.inject(this)
        setContentView(binding.root);


        val arguments = intent.extras
        closePeriodMode = arguments!!.getBoolean("EXTRA_CLOSE_PERIOD_MODE")
        purchaseId = arguments.getLong("EXTRA_PURCHASE_ID")

        vm = ViewModelProvider(this, vmFactory)[PurchaseViewModel::class.java]

        vm.getPurchase(purchaseId)
        vm.getDaughterPurchases(purchaseId)
        vm.getDaughterPurchasesSum(purchaseId)

        vm.daughterPurchases.observe(this,daughterPurchaseObserver)
        vm.daughterPurchaseSum.observe(this, daughterPurchaseSumObserver)
        vm.purchase.observe(this, purchaseObserver)
    }

    private val purchaseObserver: Observer<Purchase> =
        Observer<Purchase> { newPurchase ->
            purchase = newPurchase
            updateUi()
        }

    private val daughterPurchaseObserver: Observer<ArrayList<Purchase>> =
        Observer<ArrayList<Purchase>> { newDaughterPurchases ->
            listPurchases = newDaughterPurchases
            if (listPurchases.isNotEmpty()) {
                createRecyclerView()
            }
        }


    private val daughterPurchaseSumObserver: Observer<Float> =
        Observer<Float> { newSumDaughterPurchases ->
            sumOfChildrenSpending = newSumDaughterPurchases
            updateUi()
        }

    fun updateUi(){
        binding.profitDescription.setText(purchase.description)
        binding.profitName.setText(purchase.name)

        val v_purchaseTreeOfParents = findViewById<TextView>(R.id.purchaseTreeOfParents)
        //v_purchaseTreeOfParents.setText(Purchase.getParentsTree(this, purchaseId))
        binding.profitAmount.setText(java.lang.String.valueOf(purchase.price))
        setStatus(purchase.statusId)



        if (purchase.repeater) {
            binding.purchaseRepeat.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true))
        } else {
            binding.purchaseRepeat.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false))
        }
        binding.profitAmount.addTextChangedListener(textWatcher)
        binding.profitDate.addTextChangedListener(textWatcher)

        val v_addButton = findViewById<View>(R.id.btn_add)
        v_addButton.visibility = View.GONE
        getSum()
        buttonsPeriodAvailable()

        if (purchase.parentId == -8L) {
            binding.purchasePeriod.setText(Period.getPeriodName())
            if (purchase.periodId == mMyApp.getActualPeriod()) {
                binding.periodActive.setText(getString(R.string.active))
            } else {
                binding.periodActive.setText(getString(R.string.notActive))
            }
        } else {
            binding.purchasePeriod.setText(Period.getPeriodName())
            binding.periodActive.setText(R.string.daughterPurchase)
        }

        binding.profitDate.setText(java.lang.String.valueOf(purchase.count))

    }

     private fun setStatus(statusId: Int) {
         purchase.statusId = statusId
         val v_status_1 = findViewById<View>(R.id.purchaseStatus1)
         val v_status_2 = findViewById<View>(R.id.purchaseStatus2)
         val v_status_3 = findViewById<View>(R.id.purchaseStatus3)
         v_status_1.background = ContextCompat.getDrawable(this, R.drawable.ic_status_plan)
         v_status_2.background = ContextCompat.getDrawable(this, R.drawable.ic_status_accrued)
         v_status_3.background = ContextCompat.getDrawable(this, R.drawable.ic_status_baught)
         when (statusId) {
             100 -> v_status_1.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_status_plan_active)
             200 -> v_status_2.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_status_accrued_active)
             300 -> v_status_3.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_status_baught_active)
         }
     }

     fun repeaterSwitch(view: View?) {
         if (purchase.repeater) {
             purchase.repeater = false
             binding.purchaseRepeat!!.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false)
         } else {
             purchase.repeater = true
             binding.purchaseRepeat!!.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true)
         }
     }

     fun setStatus1(view: View?) {
         setStatus(100)
     }

     fun setStatus2(view: View?) {
         setStatus(200)
     }

     fun setStatus3(view: View?) {
         setStatus(300)
     }

     fun plusCount(view: View?) {
         purchase.count = purchase.count + 1
         binding.profitDate.setText(java.lang.String.valueOf(purchase.count))
     }

     fun minusCount(view: View?) {
         var count: Float = purchase.count
         if (count >= 1) {
             count -= 1f
             purchase.count =count
             binding.profitDate.setText(count.toString())
         }
     }

     fun editPurchase(view: View) {
         try {
             val x: Float = binding.profitAmount.getText().toString().toFloat()
             val purchaseEditable = Purchase(
                 purchaseId,
                 purchase.idFdb,
                 binding.profitName.text.toString(),
                 binding.profitDescription.text.toString(),
                 binding.profitAmount.text.toString().toFloat(),
                 binding.profitDate.text.toString().toFloat(),
                 purchase.periodId,
                 purchase.statusId,
                 parentId,
                 purchase.repeater,  //binding.purchaseRepeat.isChecked(),
             )
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
                     intent.putExtra("EXTRA_CLOSE_PERIOD_MODE", closePeriodMode)
                 } else if (closePeriodMode) {
                     intent = Intent(this, StubActivity::class.java)
                 } else {
                     intent = Intent(this, MainActivity::class.java)
                 }
                 val toast = Toast.makeText(view.context, "Данные сохранены", Toast.LENGTH_LONG)
                 toast.show()
                 startActivity(intent)
             }
         } catch (e: Exception) {
             val toast = Toast.makeText(view.context, "editPurchase error: $e", Toast.LENGTH_SHORT)
             toast.show()
         }
     }

     fun deletePurchase(view: View) {
         if (purchase.id == 0L) {
             goBack()
         }
         val builder = AlertDialog.Builder(this)
         builder.setTitle("Удалить")
         builder.setMessage("Вы уверены, что хотите удалить эту покупку?")
         //.setIcon(R.drawable.ic_launcher_cat)
         builder.setPositiveButton(
             getString(R.string.yesItIs)
         ) { dialog, id ->
             val intent: Intent
             try {
                 if (listPurchases.size < 1) {
                     //Purchase.deletePurchase(view.context, purchase)
                     if (parentId != -8L) {
                         intent = Intent(view.context, PurchaseActivity::class.java)
                         intent.putExtra("EXTRA_PURCHASE_ID", parentId)
                         intent.putExtra("EXTRA_CLOSE_PERIOD_MODE", closePeriodMode)
                     } else if (closePeriodMode) {
                         intent = Intent(view.context, StubActivity::class.java)
                     } else {
                         intent = Intent(view.context, MainActivity::class.java)
                     }
                     val toast = Toast.makeText(view.context, "Покупка удалена", Toast.LENGTH_LONG)
                     toast.show()
                     startActivity(intent)
                 } else {
                     val toast = Toast.makeText(
                         view.context,
                         "Удалить можно только пустую покупку",
                         Toast.LENGTH_SHORT
                     )
                     toast.show()
                 }
             } catch (e: Exception) {
                 val toast = Toast.makeText(
                     view.context,
                     "deletePurchase error: $e", Toast.LENGTH_SHORT
                 )
                 toast.show()
             }
         }
         builder.setNegativeButton(
             getString(R.string.noItIsNot)
         ) { dialog, id -> dialog.cancel() }
         builder.show()
     }

     fun createRecyclerView() {
         val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
         linearLayoutManager.reverseLayout = false
         linearLayoutManager.stackFromEnd = false
         recyclerView = findViewById(R.id.mainPurchasesRecyclerView)
         recyclerView.setHasFixedSize(false)
         recyclerView.setLayoutManager(linearLayoutManager)
         adapter = PurchaseAdapter(listPurchases, this)
         recyclerView.setAdapter(adapter)
         adapter.setListener(
             object : PurchaseAdapter.Listener {
                 override fun onClick(view: View, position: Long) {
                     val intent = Intent(view.context, StubActivity::class.java)
                     intent.putExtra("EXTRA_PURCHASE_ID", position)
                     intent.putExtra("EXTRA_CLOSE_PERIOD_MODE", closePeriodMode)
                     startActivity(intent)
                 }
             }
         )
     }

     fun addPurchase(view: View?) {
         val intent = Intent(this, PurchaseActivity::class.java)
         intent.putExtra("EXTRA_PURCHASE_ID", "0")
         intent.putExtra("EXTRA_PARENT_ID", purchaseId)
         intent.putExtra("EXTRA_CLOSE_PERIOD_MODE", closePeriodMode)
         startActivity(intent)
     }

     private fun getSum() {
         try {
             binding.purchaseSumm.setText(
                 binding.profitAmount.getText().toString() + " * "
                         + binding.profitDate.getText().toString() + " = "
                         + (binding.profitAmount.getText().toString().toFloat() * binding.profitDate.getText().toString()
                     .toFloat()).toString()
             )
         } catch (e: Exception) {
             binding.purchaseSumm.setText("0")
         }
     }

     private val textWatcher: TextWatcher = object : TextWatcher {
         override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
         override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
             getSum()
         }

         override fun afterTextChanged(s: Editable) {
             getSum()
         }
     }

     fun nextPeriod(view: View?) {
         changePeriod(+1)
     }

     fun previousPeriod(view: View?) {
         changePeriod(-1)
     }

     fun buttonBack(view: View?) {
         goBack()
     }

     private fun changePeriod(position: Int) {
        /* var periodId: Int = purchase.periodId
         if (Period.neighborIsAvailable(this, periodId, position) && purchase.parentId == -8L
         ) {
             purchase.periodId = (periodId + position)
             periodId = periodId + position
             binding.purchasePeriod.setText(Period.getPeriodName(this, periodId))
             buttonsPeriodAvailable()
             mMyApp.setCurrentPeriod(periodId)
             if (periodId == mMyApp.getActualPeriod()) { //todo function
                 binding.periodActive.setText(getString(R.string.active))
             } else {
                 binding.periodActive.setText(getString(R.string.notActive))
             }
         }*/
     }

     fun buttonsPeriodAvailable() {
         /*
         val previousPeriod = findViewById<View>(R.id.purchaseButtonPreviousPeriod)
         val nextPeriod = findViewById<View>(R.id.purchaseButtonNextPeriod)
         val periodId: Int = purchase.periodId()
         if (purchase.parentId().equals("main")) {
             if (Period.neighborIsAvailable(this, periodId, -1)) {
                 previousPeriod.background =
                     ContextCompat.getDrawable(this, R.drawable.ic_arrow_left)
             } else {
                 previousPeriod.background =
                     ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_off)
             }
             if (Period.neighborIsAvailable(this, periodId, +1)) {
                 nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right)
             } else {
                 nextPeriod.background =
                     ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_off)
             }
         } else {
             nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_off)
             previousPeriod.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_off)
         }*/
     }

     fun goBack() {
         val intent: Intent
         if (parentId != -8L) {
             intent = Intent(this, PurchaseActivity::class.java)
             intent.putExtra("EXTRA_PURCHASE_ID", parentId)
             intent.putExtra("EXTRA_CLOSE_PERIOD_MODE", closePeriodMode)
         } else if (closePeriodMode) {
             intent = Intent(this, StubActivity::class.java)
         } else {
             intent = Intent(this, MainActivity::class.java)
         }
         startActivity(intent)
     }

     override fun onBackPressed() {
         goBack()
     }
}

