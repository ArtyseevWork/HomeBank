package com.mordansoft.homebank.ui.profits

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.ProfitsAccounting
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.ui.main.MainActivity
import com.mordansoft.homebank.ui.main.MainViewModel
import com.mordansoft.homebank.ui.main.MainViewModelFactory
import com.mordansoft.homebank.ui.main.PurchaseAdapter
import com.mordansoft.homebank.ui.profit.ProfitActivity
import com.mordansoft.homebank.ui.purchase.PurchaseActivity

import java.lang.String
import kotlin.Float
import kotlin.Int


class ProfitsActivity : AppCompatActivity() {
    private var periodId = 0

    lateinit var myApp: App
    private var listProfits: ArrayList<Profit> = ArrayList()
    private var profitsAccounting = ProfitsAccounting()
    private var period : Period = Period()
    private lateinit var vm : ProfitsViewModel
    @javax.inject.Inject
    lateinit var vmFactory: ProfitsViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profits)
        myApp = this.applicationContext as App
        myApp.appComponent.inject(this)

        vm = ViewModelProvider(this, vmFactory)[ProfitsViewModel::class.java]
        vm.getPeriodsData(null)

        vm.period.observe(this, mainPeriodObserver)
        vm.profits.observe(this,mainProfitsObserver)
        vm.accounting.observe(this,accountingObserver)



        /*val tvActiveStatus = findViewById<TextView>(R.id.profitsActiveStatus) //todo
        if (periodId == myApp.getActualPeriod()) {
            tvActiveStatus.text = getString(R.string.active)
        } else {
            tvActiveStatus.text = getString(R.string.notActive)
        }*/

    }

    fun updateUi(){
        //createRecyclerView(R.id.profitsRecyclerView)
        val v_plan = findViewById<TextView>(R.id.activity_profits__plan_budget)
        val v_fact = findViewById<TextView>(R.id.activity_profits__fact_budget)
        val v_period = findViewById<TextView>(R.id.profitsPeriod)
        v_plan.setText(String.valueOf(profitsAccounting.capitalPlan))
        v_fact.setText(String.valueOf(profitsAccounting.capitalFact))
        v_period.text = period.name
        buttonsPeriodAvailable()
    }

    private val mainProfitsObserver: Observer<ArrayList<Profit>> =
        Observer<ArrayList<Profit>> { newMainProfits ->
            listProfits = newMainProfits
            createRecyclerView()
        }

    private val accountingObserver: Observer<ProfitsAccounting> =
        Observer<ProfitsAccounting> { newAccounting ->
            profitsAccounting = newAccounting
            updateUi()
        }


    private val mainPeriodObserver: Observer<Period> =
        Observer<Period> { newPeriod ->
            period = newPeriod
            updateUi()
        }

    fun createRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        linearLayoutManager.reverseLayout = false
        linearLayoutManager.stackFromEnd = false
        val recyclerView = findViewById<RecyclerView>(R.id.profitsRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = ProfitAdapter(listProfits , this)
        recyclerView.adapter = adapter
        adapter.setListener(
            object : ProfitAdapter.Listener {
                override fun onClick(view: View, position: Long) {
                    val intent = Intent(view.context, ProfitActivity::class.java)
                    intent.putExtra("EXTRA_PROFIT_ID", position)
                    startActivity(intent)
                }
            }
        )
    }


    fun previousPeriod(view: View?) {
        changePeriod(period.previousPeriodId)
    }

    fun nextPeriod(view: View?) {
        changePeriod(period.nextPeriodId)
    }

    private fun changePeriod(newPeriodId: Int) {
        if (newPeriodId != 0) {
            vm.getPeriodsData(newPeriodId)
        }
    }

    private fun buttonsPeriodAvailable() {
        val previousPeriod = findViewById<View>(R.id.buttonPreviousPeriod)
        if (period.previousPeriodId != 0) {
            previousPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left)
        } else {
            previousPeriod.background =
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_off)
        }
        val nextPeriod = findViewById<View>(R.id.buttonNextPeriod)
        if (period.nextPeriodId != 0) {
            nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right)
        } else {
            nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_off)
        }
    }

    fun addProfit(view: View?) {
        val intent = Intent(this, ProfitActivity::class.java)
        intent.putExtra("EXTRA_PROFIT_ID", "0")
        startActivity(intent)
    }

    fun buttonBack(view: View?) {
        goBack()
    }

    fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        goBack()
    }
}