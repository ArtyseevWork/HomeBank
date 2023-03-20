package com.mordansoft.homebank.ui.profit

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.domain.model.Profit
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.mordansoft.homebank.databinding.ActivityProfitBinding
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.ui.profits.ProfitsActivity

class ProfitActivity : AppCompatActivity() {

    var dateFormat = SimpleDateFormat("dd-MM-yyyy")
    var profit: Profit = Profit()
    var period : Period = Period()
    var normalTime: Date = Date()
    var periodId : Int = Period.DEFAULT_ID
    var profitId : Long = Profit.DEFAULT_ID

    private lateinit var vm : ProfitViewModel

    @javax.inject.Inject
    lateinit var vmFactory: ProfitViewModelFactory
    private val binding by lazy {
        ActivityProfitBinding.inflate(layoutInflater);
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root);
        val mMyApp: App = this.applicationContext as App
        mMyApp.appComponent.inject(this)
        val arguments = intent.extras
        if (arguments != null) {
            profitId = arguments.getLong("EXTRA_PROFIT_ID")
            periodId = arguments.getInt("EXTRA_PERIOD_ID")
        }
        vm = ViewModelProvider(this, vmFactory)[ProfitViewModel::class.java]
        vm.getProfit(profitId)
        vm.profit.observe(this,profitObserver)
        vm.period.observe(this, mainPeriodObserver)
    }

    private val profitObserver: androidx.lifecycle.Observer<Profit> =
        Observer<Profit> { newProfit ->
            profit = newProfit
            periodId = profit.periodId
            vm.getPeriod(periodId)
            updateUi()
        }

    private val mainPeriodObserver: Observer<Period> =
        Observer<Period> { newPeriod -> // Update the UI, in this case, a TextView.
            period = newPeriod
            updateUi()
        }


    private fun updateUi(){
        binding.profitName.setText(profit.name)
        setDate()
        binding.profitAmount.setText(java.lang.String.valueOf(profit.amount))
        binding.profitPeriod.setText(period.name)
        binding.profitDescription.setText(profit.description)
        if (profit.repeater) {
            binding.purchaseRepeat.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true))
        } else {
            binding.purchaseRepeat.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false))
        }
        setStatus(profit.statusId)
    }

    fun addEditProfit(view: View) {
        try {
            val profitEditable = Profit(
                id = profit.id,
                        name = binding.profitName.text.toString(),
                        description = binding.profitDescription.text.toString(),
                        amount = binding.profitAmount.text.toString().toFloat(),
                        date = profit.date,
                        periodId = periodId,
                        statusId = profit.statusId,
                        repeater = profit.repeater
            )
            vm.setProfit(profitEditable)

            val toast = Toast.makeText(view.context, "Данные сохранены", Toast.LENGTH_LONG)
            toast.show()
        } catch (e: Exception) {
            val toast = Toast.makeText(view.context, "addEditProfit error: $e", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun deleteProfit(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Удалить")
        builder.setMessage("Вы уверены, что хотите удалить этот Доход?")
        builder.setPositiveButton(
            getString(R.string.yesItIs)
        ) { dialog: DialogInterface?, id: Int ->
            try {
                vm.deleteProfit(profit)
                val toast =
                    Toast.makeText(view.context, "Данные Удалены", Toast.LENGTH_LONG)
                toast.show()
            } catch (e: Exception) {
                val toast = Toast.makeText(
                    view.context,
                    "deleteProfit error: $e",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        builder.setNegativeButton(
            getString(R.string.noItIsNot)
        ) { dialog: DialogInterface, id: Int -> dialog.cancel() }
        builder.show()
    }

    fun setDate() {
        normalTime = Date(profit.date * 1000)
        binding.profitDate.setText(dateFormat.format(normalTime))
    }

    fun setStatus1(view: View?) {
        setStatus(Profit.STATUS_PLANNED)
    }

    fun setStatus2(view: View?) {
        setStatus(Profit.STATUS_ACCRUED)
    }

    fun setStatus3(view: View?) {
        setStatus(Profit.STATUS_RECEIVED)
    }

    fun setStatus(statusId: Int) {
        profit.statusId = (statusId)
        val v_status_1 = findViewById<View>(R.id.purchaseStatus1)
        val v_status_2 = findViewById<View>(R.id.purchaseStatus2)
        val v_status_3 = findViewById<View>(R.id.purchaseStatus3)
        v_status_1.background = ContextCompat.getDrawable(this, R.drawable.ic_status_plan)
        v_status_2.background = ContextCompat.getDrawable(this, R.drawable.ic_status_accrued)
        v_status_3.background = ContextCompat.getDrawable(this, R.drawable.ic_status_canceled)
        when (statusId) {
            Profit.STATUS_PLANNED -> v_status_1.background =
                ContextCompat.getDrawable(this, R.drawable.ic_status_plan_active)
            Profit.STATUS_ACCRUED -> v_status_2.background =
                ContextCompat.getDrawable(this, R.drawable.ic_status_accrued_active)
            Profit.STATUS_RECEIVED -> v_status_3.background =
                ContextCompat.getDrawable(this, R.drawable.ic_status_canceled_active)
        }
    }

    fun repeaterSwitch(view: View?) {
        if (profit.repeater) {
            profit.repeater = (false)
            binding.purchaseRepeat.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false)
        } else {
            profit.repeater =(true)
            binding.purchaseRepeat.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true)
        }
    }

    fun plusDate(view: View?) {
        val dateOld: Long = profit.date
        profit.date = (dateOld + Period.ONE_DAY_MILLIS)
        setDate()
    }

    fun minusDate(view: View?) {
        val dateOld: Long = profit.date
        profit.date = (dateOld - Period.ONE_DAY_MILLIS)
        setDate()
    }


    fun buttonBack(view: View?) {
        goBack()
    }

    private fun goBack() {
        val intent = Intent(this, ProfitsActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        goBack()
    }
}