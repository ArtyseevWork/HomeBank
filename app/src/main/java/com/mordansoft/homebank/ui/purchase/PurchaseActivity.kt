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
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.ui.StubActivity
import com.mordansoft.homebank.ui.main.MainActivity
import com.mordansoft.homebank.ui.main.PurchaseAdapter

class PurchaseActivity : AppCompatActivity() {
    private var periodId: Int = Period.DEFAULT_ID
    private lateinit var adapter: PurchaseAdapter
    var period  : Period = Period()
    var purchase: Purchase = Purchase()
    private var listPurchases: ArrayList<Purchase> = ArrayList()
    private var purchaseId : Long = Purchase.DEFAULT_ID
    private var parentId   : Long = Purchase.DEFAULT_PARENT
    private lateinit var vm : PurchaseViewModel
    private var sumOfChildrenSpending: Float = 0F

    @javax.inject.Inject
    lateinit var vmFactory: PurchaseViewModelFactory


    private val binding by lazy {
        ActivityPurchaseBinding.inflate(layoutInflater);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(binding.root);

        val arguments = intent.extras
        if (arguments != null) {
            purchaseId = arguments.getLong("EXTRA_PURCHASE_ID")
            parentId   = arguments.getLong("EXTRA_PARENT_ID")
            periodId   = arguments.getInt ("EXTRA_PERIOD_ID")
        }

        vm = ViewModelProvider(this, vmFactory)[PurchaseViewModel::class.java]

        vm.getPurchase(purchaseId)
        vm.getDaughterPurchases(purchaseId)
        vm.getDaughterPurchasesSum(purchaseId)

        vm.daughterPurchases.observe(this,daughterPurchaseObserver)
        vm.daughterPurchaseSum.observe(this, daughterPurchaseSumObserver)
        vm.purchase.observe(this, purchaseObserver)
        vm.period.observe(this, mainPeriodObserver)
    }

    private val purchaseObserver: Observer<Purchase> =
        Observer<Purchase> { newPurchase ->
            purchase = newPurchase
            vm.getPeriod(purchase.periodId)
            updateUi()
        }

    private val daughterPurchaseObserver: Observer<ArrayList<Purchase>> =
        Observer<ArrayList<Purchase>> { newDaughterPurchases ->
            listPurchases = newDaughterPurchases
            if (listPurchases.isNotEmpty()) {
                createRecyclerView()
            }
        }

    private val mainPeriodObserver: Observer<Period> =
        Observer<Period> { newPeriod -> // Update the UI, in this case, a TextView.
            period = newPeriod
            updateUi()
        }

    private val daughterPurchaseSumObserver: Observer<Float> =
        Observer<Float> { newSumDaughterPurchases ->
            sumOfChildrenSpending = newSumDaughterPurchases
            updateUi()
        }

    private fun updateUi(){
        binding.profitDescription.setText(purchase.description)
        binding.profitName.setText(purchase.name)

        val v_purchaseTreeOfParents = findViewById<TextView>(R.id.purchaseTreeOfParents)//todo
        //v_purchaseTreeOfParents.setText(Purchase.getParentsTree(this, purchaseId))
        binding.profitAmount.setText(java.lang.String.valueOf(purchase.price))
        setStatus(purchase.statusId)



        if (purchase.repeater) {
            binding.purchaseRepeat.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true)
        } else {
            binding.purchaseRepeat.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false)
        }
        binding.profitAmount.addTextChangedListener(textWatcher)
        binding.profitDate.addTextChangedListener(textWatcher)

        val v_addButton = findViewById<View>(R.id.btn_add)
        if (purchase.id == Purchase.DEFAULT_ID ){ // new purchase
            v_addButton.visibility = View.GONE
        } else {
            v_addButton.visibility = View.VISIBLE
        }

        getSum()

        binding.purchasePeriod.text = period.name

        /*if (purchase.periodId == mMyApp.getActualPeriod()) {// todo period status
            binding.periodActive.setText(getString(R.string.active))
        } else {
            binding.periodActive.setText(getString(R.string.notActive))
        }*/

        binding.purchasePeriod.text = period.name
        binding.profitDate.setText(purchase.count.toString())
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
             Purchase.STATUS_PLANNED -> v_status_1.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_status_plan_active)
             Purchase.STATUS_DELAYED -> v_status_2.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_status_accrued_active)
             Purchase.STATUS_PURCHASED -> v_status_3.background =
                 ContextCompat.getDrawable(this, R.drawable.ic_status_baught_active)
         }
     }

     fun repeaterSwitch(view: View?) {
         if (purchase.repeater) {
             purchase.repeater = false
             binding.purchaseRepeat.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false)
         } else {
             purchase.repeater = true
             binding.purchaseRepeat.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true)
         }
     }

     fun setStatus1(view: View?) {
         setStatus(Purchase.STATUS_PLANNED)
     }

     fun setStatus2(view: View?) {
         setStatus(Purchase.STATUS_DELAYED)
     }

     fun setStatus3(view: View?) {
         setStatus(Purchase.STATUS_PURCHASED)
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
         val purchaseEditable = Purchase(
             id          = purchaseId,
             name        = binding.profitName.text.toString(),
             description = binding.profitDescription.text.toString(),
             price       = binding.profitAmount.text.toString().toFloat(),
             count       = binding.profitDate.text.toString().toFloat(),
             periodId    = purchase.periodId,
             statusId    = purchase.statusId,
             parentId    = parentId,
             repeater    = purchase.repeater,
         )
         vm.setPurchase(purchaseEditable)
         val toast = Toast.makeText(view.context, "Данные сохранены", Toast.LENGTH_LONG)
         toast.show()
     }

     fun deletePurchase(view: View) {
         val builder = AlertDialog.Builder(this)
         builder.setTitle("Удалить")
         builder.setMessage("Вы уверены, что хотите удалить эту покупку?")
         builder.setPositiveButton(
             getString(R.string.yesItIs)
         ) { dialog, id ->
             val intent: Intent
             try {
                 if (listPurchases.isEmpty()) {
                     vm.deletePurchase(purchase)
                     val toast = Toast.makeText(view.context, "Покупка удалена", Toast.LENGTH_LONG)
                     toast.show()
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

     private fun createRecyclerView() {
         val recyclerView: RecyclerView = findViewById(R.id.childPurchasesRecyclerView)
         val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
         linearLayoutManager.reverseLayout = false
         linearLayoutManager.stackFromEnd = false
         recyclerView.setHasFixedSize(false)
         recyclerView.setLayoutManager(linearLayoutManager)
         adapter = PurchaseAdapter(listPurchases, this)
         recyclerView.setAdapter(adapter)
         adapter.setListener(
             object : PurchaseAdapter.Listener {
                 override fun onClick(view: View, position: Long) {
                     val intent = Intent(view.context, StubActivity::class.java)
                     intent.putExtra("EXTRA_PURCHASE_ID", position)
                     startActivity(intent)
                 }
             }
         )
     }

     fun addPurchase(view: View?) {
         val intent = Intent(this, PurchaseActivity::class.java)
         intent.putExtra("EXTRA_PURCHASE_ID", Purchase.DEFAULT_ID) // new Purchase
         intent.putExtra("EXTRA_PARENT_ID", purchaseId)
         intent.putExtra("EXTRA_PERIOD_ID", period.id)
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

     fun buttonBack(view: View?) {
         goBack()
     }

     fun goBack() {
         val intent: Intent
         if (parentId != Purchase.DEFAULT_PARENT) {
             intent = Intent(this, PurchaseActivity::class.java)
             intent.putExtra("EXTRA_PURCHASE_ID", parentId)
         } else {
             intent = Intent(this, MainActivity::class.java)
         }
         startActivity(intent)
     }

     override fun onBackPressed() {
         goBack()
     }
}

