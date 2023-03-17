package com.mordansoft.homebank.ui.profits

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.domain.model.Profit
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
    var v_period: TextView? = null
    var v_plan: TextView? = null
    var v_fact: TextView? = null
    lateinit var myApp: App
    var onlineMode = false
    private var listProfits: ArrayList<Profit> = ArrayList()

    private lateinit var vm : ProfitsViewModel

    @javax.inject.Inject
    lateinit var vmFactory: ProfitsViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = this.applicationContext as App
        setContentView(R.layout.activity_profits)

        myApp.appComponent.inject(this)
        //periodId = myApp.getCurrentPeriod()
        //onlineMode = myApp.getOnlineMode()
        periodId = 8
        onlineMode = false

        vm = ViewModelProvider(this, vmFactory)[ProfitsViewModel::class.java]

        vm.getProfits();
        //vm.getMainPurchasesMutableLiveData().observe(this,mainPurchasesObserver)
        vm.profits.observe(this,mainProfitsObserver)



        v_period = findViewById<TextView>(R.id.profitsPeriod)

        val tvActiveStatus = findViewById<TextView>(R.id.profitsActiveStatus)
        if (periodId == myApp.getActualPeriod()) {
            tvActiveStatus.text = getString(R.string.active)
        } else {
            tvActiveStatus.text = getString(R.string.notActive)
        }

    }

    fun updateUi(){
        //createRecyclerView(R.id.profitsRecyclerView)
        v_plan = findViewById<TextView>(R.id.activity_profits__plan_budget)
        v_fact = findViewById<TextView>(R.id.activity_profits__fact_budget)
        //v_plan.setText(String.valueOf(Profit.getPeriodProfits(this, periodId, 0, false)))
        //var periodProfits: Float = Profit.getPeriodProfits(this, periodId, 200, false)
        //periodProfits += Profit.getPeriodProfits(this, periodId, 300, false)
       // v_fact.setText(periodProfits.toString())
        v_period = findViewById<TextView>(R.id.profitsPeriod)
        //v_period.setText(myApp.getCurrentPeriodName())
       // buttonsPeriodAvailable()
    }

    private val mainProfitsObserver: Observer<ArrayList<Profit>> =
        Observer<ArrayList<Profit>> { newMainProfits -> // Update the UI, in this case, a TextView.
            listProfits = newMainProfits
            createRecyclerView()
            updateUi()
        }



    /*protected fun createFdbListeners() {
        if (onlineMode) {
            profitsEventListener = ChildEventListeners.getProfitEventListener(this)
            profitsQuery = FirebaseHelper.profitsSync(profitsEventListener)
        }
    }*/

    /*protected fun removeFdbListeners() {
        if (onlineMode) {
            profitsQuery.removeEventListener(profitsEventListener)
        }
    }*/

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

    /*fun previousPeriod(view: View?) {
        changePeriod(-1)
    }*/

    /*fun nextPeriod(view: View?) {
        changePeriod(+1)
    }*/

    /*private fun changePeriod(position: Int) {
        if (Period.neighborIsAvailable(this, periodId, position)) {
            val intent = Intent(this, ProfitsActivity::class.java)
            mMyApp.setCurrentPeriod(periodId + position)
            startActivity(intent)
        }
    }*/

    /*fun buttonsPeriodAvailable() {
        val previousPeriod = findViewById<View>(R.id.buttonPreviousPeriod)
        if (Period.neighborIsAvailable(this, periodId, -1)) {
            previousPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left)
        } else {
            previousPeriod.background =
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_off)
        }
        val nextPeriod = findViewById<View>(R.id.buttonNextPeriod)
        if (Period.neighborIsAvailable(this, periodId, +1)) {
            nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right)
        } else {
            nextPeriod.background = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_off)
        }
    }*/

    /*fun addProfit(view: View?) {
        val intent = Intent(this, ProfitActivity::class.java)
        intent.putExtra("EXTRA_PROFIT_ID", "0")
        startActivity(intent)
    }*/

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