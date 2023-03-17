package com.mordansoft.homebank.ui.profit

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.domain.model.Profit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class ProfitActivity : AppCompatActivity() {
    private lateinit var v_period: TextView
    private lateinit var v_name: EditText
    private lateinit var v_date: EditText
    private lateinit var v_amount: EditText
    private lateinit var v_description: EditText
    private lateinit var v_repeat: View
    private var closePeriodMode = false
    protected lateinit var mMyApp: App
    var dateFormat = SimpleDateFormat("dd-MM-yyyy")
    lateinit var profit: Profit
    lateinit var normalTime: Date

    private lateinit var vm : ProfitViewModel

    @javax.inject.Inject
    lateinit var vmFactory: ProfitViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMyApp = this.applicationContext as App
        val arguments = intent.extras
        val profitId = arguments!!.getLong("EXTRA_PROFIT_ID")
        closePeriodMode = arguments.getBoolean("EXTRA_CLOSE_PERIOD_MODE")
        setContentView(R.layout.activity_profit)

        mMyApp.appComponent.inject(this)

        vm = ViewModelProvider(this, vmFactory)[ProfitViewModel::class.java]

        vm.getProfit(profitId)
        vm.profit.observe(this,profitObserver)
    }

    private val profitObserver: androidx.lifecycle.Observer<Profit> =
        Observer<Profit> { newProfit ->
            profit = newProfit
            updateUi()
        }

    fun updateUi(){
        v_name = findViewById<EditText>(R.id.profitName)
        v_name.setText(profit.name)
        setDate()
        v_amount = findViewById<EditText>(R.id.profitAmount)
        v_amount.setText(java.lang.String.valueOf(profit.amount))
        v_period = findViewById<TextView>(R.id.profitPeriod)
       // v_period.setText(mMyApp.getCurrentPeriodName())
        val v_period_active = findViewById<TextView>(R.id.periodActive)
        if (profit.periodId=== mMyApp.getActualPeriod()) {
            v_period_active.text = getString(R.string.active)
        } else {
            v_period_active.text = getString(R.string.notActive)
        }
        v_description = findViewById<EditText>(R.id.profitDescription)
        v_description.setText(profit.description)
        v_repeat = findViewById<View>(R.id.purchaseRepeat)
        if (profit.repeater) {
            v_repeat.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true))
        } else {
            v_repeat.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false))
        }
        setStatus(profit.statusId)
    }

   /* fun addEditProfit(view: View) {
        val unixTime: Long
        try {
            val profitId: Long = profit.id
            val profitIdFdb: Long = profit.idFdb
            unixTime = try {
                val date = dateFormat.parse(v_date!!.text.toString()) as Date
                date.time / 1000
            } catch (e: ParseException) {
                val intent = Intent(this, ProfitActivity::class.java)
                intent.putExtra("EXTRA_PROFIT_ID", profitId)
                startActivity(intent)
                val toast = Toast.makeText(view.context, "Неверный тип даты", Toast.LENGTH_LONG)
                toast.show()
                return
            }
            val profitEditable = Profit(
                id = profitId,
                profitIdFdb,
                v_name!!.text.toString(),
                v_description!!.text.toString(),
                v_amount!!.text.toString().toFloat(),
                profit.periodId,
                unixTime,
                profit.statusId,
                profit.repeater,
                System.currentTimeMillis()
            )
            if (profitId == "0") {
                Profit.insertProfit(view.context, profitEditable)
            } else {
                Profit.updateProfit(view.context, profitEditable, false)
            }
            val toast = Toast.makeText(view.context, "Данные сохранены", Toast.LENGTH_LONG)
            val intent: Intent
            intent = if (closePeriodMode) {
                Intent(view.context, closePeriodProfitsActivity::class.java)
            } else {
                Intent(view.context, ProfitsActivity::class.java)
            }
            toast.show()
            startActivity(intent)
        } catch (e: Exception) {
            val toast = Toast.makeText(view.context, "addEditProfit error: $e", Toast.LENGTH_SHORT)
            toast.show()
        }
    }*/

    /*fun deleteProfit(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Удалить")
        builder.setMessage("Вы уверены, что хотите удалить этот Доход?")
        builder.setPositiveButton(
            getString(R.string.yesItIs)
        ) { dialog: DialogInterface?, id: Int ->
            try {
                Profit.deleteProfit(view.context, profit)
                val toast =
                    Toast.makeText(view.context, "Данные Удалены", Toast.LENGTH_LONG)
                val intent: Intent
                intent = if (closePeriodMode) {
                    Intent(view.context, closePeriodProfitsActivity::class.java)
                } else {
                    Intent(view.context, MainActivity::class.java)
                }
                toast.show()
                startActivity(intent)
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
    }*/

    fun setDate() {
        v_date = findViewById<EditText>(R.id.profitDate)
        normalTime = Date(profit.date * 1000)
        v_date.setText(dateFormat.format(normalTime))
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

    fun setStatus(statusId: Int) {
        profit.statusId = (statusId)
        val v_status_1 = findViewById<View>(R.id.purchaseStatus1)
        val v_status_2 = findViewById<View>(R.id.purchaseStatus2)
        val v_status_3 = findViewById<View>(R.id.purchaseStatus3)
        v_status_1.background = ContextCompat.getDrawable(this, R.drawable.ic_status_plan)
        v_status_2.background = ContextCompat.getDrawable(this, R.drawable.ic_status_accrued)
        v_status_3.background = ContextCompat.getDrawable(this, R.drawable.ic_status_canceled)
        when (statusId) {
            100 -> v_status_1.background =
                ContextCompat.getDrawable(this, R.drawable.ic_status_plan_active)
            200 -> v_status_2.background =
                ContextCompat.getDrawable(this, R.drawable.ic_status_accrued_active)
            300 -> v_status_3.background =
                ContextCompat.getDrawable(this, R.drawable.ic_status_canceled_active)
        }
    }

    fun repeaterSwitch(view: View?) {
        if (profit.repeater) {
            profit.repeater = (false)
            v_repeat!!.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_false)
        } else {
            profit.repeater =(true)
            v_repeat!!.background = ContextCompat.getDrawable(this, R.drawable.ic_checkbox_true)
        }
    }

    fun plusDate(view: View?) {
        val dateOld: Long = profit.date
        profit.date = (dateOld + 86400)
        setDate()
    }

    fun minusDate(view: View?) {
        val dateOld: Long = profit.date
        profit.date = (dateOld - 86400)
        setDate()
    }

    /*fun nextPeriod(view: View?) {
        changePeriod(+1)
    }*/

    /*fun previousPeriod(view: View?) {
        changePeriod(-1)
    }*/

    /*private fun changePeriod(position: Int) {
        var periodId: Int = profit.periodI
        if (Period.neighborIsAvailable(this, periodId, position)) {
            profit.setPeriodId(periodId + position)
            periodId = periodId + position
            v_period.setText(Period.getPeriodName(this, periodId))
            buttonsPeriodAvailable()
            mMyApp.setCurrentPeriod(periodId)
        }
    }*/

   /*fun buttonsPeriodAvailable() {
        val periodId: Int = profit.getPeriodId()
        val previousPeriod = findViewById<View>(R.id.profitButtonPreviousPeriod)
        if (Period.neighborIsAvailable(this, periodId, -1)) {
            previousPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left)
        } else {
            previousPeriod.background =
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_off)
        }
        val nextPeriod = findViewById<View>(R.id.profitButtonNextPeriod)
        if (Period.neighborIsAvailable(this, periodId, +1)) {
            nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right)
        } else {
            nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_off)
        }
    }*/

   /* fun buttonBack(view: View?) {
        goBack()
    }

    fun goBack() {
        val intent: Intent
        intent = if (closePeriodMode) {
            Intent(this, closePeriodProfitsActivity::class.java)
        } else {
            Intent(this, ProfitsActivity::class.java)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        goBack()
    }*/
}