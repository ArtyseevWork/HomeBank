package com.mordansoft.homebank.ui.main

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.PeriodAccounting
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.ui.StubActivity
import com.mordansoft.homebank.ui.profits.ProfitsActivity
import com.mordansoft.homebank.ui.purchase.PurchaseActivity


class MainActivity : AppCompatActivity() {

    private var listPurchases: ArrayList<Purchase> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private var currentFilterStatus = 0
    private lateinit var adapter: PurchaseAdapter
    private lateinit var vm : MainViewModel
    private var period : Period  = Period()
    private var back_pressed: Long = 0

    @javax.inject.Inject
    lateinit var vmFactory: MainViewModelFactory
    private  var periodAccounting = PeriodAccounting()
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main_new)
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]

        vm.getPeriodsData(null)
        vm.accounting.observe(this, mainAccountingObserver)
        vm.period.observe(this, mainPeriodObserver)
        vm.purchases.observe(this,mainPurchasesObserver)


        /******** left menu  *********/
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        //navigationView.setNavigationItemSelectedListener(this)
        try {
            val mDragger = drawer.javaClass.getDeclaredField(
                "mLeftDragger"
            ) //mRightDragger for right obviously
            mDragger.isAccessible = true
            val draggerObj = mDragger[drawer] as ViewDragHelper
            val mEdgeSize = draggerObj.javaClass.getDeclaredField(
                "mEdgeSize"
            )
            mEdgeSize.isAccessible = true
            //int edge = mEdgeSize.getInt(draggerObj);
            mEdgeSize.setInt(draggerObj, 0) // вот тут меняем размер.
        } catch (e: Exception) {
            Toast.makeText(
                baseContext, "left menu error",
                Toast.LENGTH_SHORT
            ).show()
        }
        //setStatus(mMyApp.getStatusInMainActivity())
    } // ! end onCreate

    private fun updateUi(){
        val v_plan_budget = findViewById<TextView>(R.id.activity_main__plan_budget)
        val v_fact_budget = findViewById<TextView>(R.id.activity_main__fact_budget)
        val v_plan_spent = findViewById<TextView>(R.id.activity_main__plan_spent)
        val v_fact_spent = findViewById<TextView>(R.id.activity_main__fact_spent)
        val v_plan_balance = findViewById<TextView>(R.id.activity_main__plan_balance)
        val v_fact_balance = findViewById<TextView>(R.id.activity_main__fact_balance)

        v_plan_budget.text = String.format("%.0f",  periodAccounting.capitalPlan)
        v_fact_budget.text = String.format("%.0f",  periodAccounting.capitalFact)
        v_plan_spent.text = String.format("%.0f",   periodAccounting.expencesPlan)
        v_fact_spent.text = String.format("%.0f",   periodAccounting.expencesFact)
        v_plan_balance.text = String.format("%.0f", periodAccounting.balancePlan)
        v_fact_balance.text = String.format("%.0f", periodAccounting.balanceFact)

        val tvPeriodNow = findViewById<TextView>(R.id.mainPeriod)
        tvPeriodNow.text = period.name
        val tvActiveStatus = findViewById<TextView>(R.id.mainActiveStatus)
        /*if (periodId == mMyApp.getActualPeriod()) { //todo status - period Name
            tvActiveStatus.text = getString(R.string.active)
        } else {
            tvActiveStatus.text = getString(R.string.notActive)
        }*/
        buttonsPeriodAvailable()
    }


    /********* observers **********/
    private val mainPeriodObserver: Observer<Period> =
        Observer<Period> { newPeriod -> // Update the UI, in this case, a TextView.
            period = newPeriod
            updateUi()
        }


    private val mainPurchasesObserver: Observer<ArrayList<Purchase>> =
        Observer<ArrayList<Purchase>> { newMainPurchases -> // Update the UI, in this case, a TextView.
            listPurchases = newMainPurchases
            createRecyclerView()
        }


    private val mainAccountingObserver: Observer<PeriodAccounting> =
        Observer<PeriodAccounting> { newAccounting -> // Update the UI, in this case, a TextView.
            periodAccounting = newAccounting
            updateUi()
        }

    /******* ! observers **********/


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
                    val intent = Intent(view.context, PurchaseActivity::class.java)
                    intent.putExtra("EXTRA_PURCHASE_ID", position)
                    startActivity(intent)
                }
            }
        )
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean { //buttons of left menu
        val id = item.itemId
        val intent: Intent
        when (id) {
            R.id.menuClosePeriod -> {
                /*val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.closePeriod))
                builder.setMessage(getString(R.string.closePeriodMessage))
                //.setIcon(R.drawable.ic_launcher_cat)
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, id ->
                    val intent = Intent(
                        applicationContext,
                        closePeriodPurchasesActivity::class.java
                    )
                    dialog.cancel()
                    startActivity(intent)
                }
                builder.setNegativeButton(
                    getString(R.string.cancelButton)
                ) { dialog, id -> dialog.cancel() }
                builder.show()
                return true*/
                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
            R.id.menuClearPeriod -> {
                /*val builder2 = AlertDialog.Builder(this)
                builder2.setTitle(getString(R.string.clearPeriod))
                builder2.setMessage(getString(R.string.clearPeriodMessage))
                //.setIcon(R.drawable.ic_launcher_cat)
                builder2.setPositiveButton(
                    getString(R.string.deleteAll)
                ) { dialog, id ->
                    Period.clearPeriod(applicationContext)
                    dialog.cancel()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                builder2.setNegativeButton(
                    getString(R.string.cancelButton)
                ) { dialog, id -> dialog.cancel() }
                builder2.show()
                return true*/
                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
            R.id.menuAuth -> {
                /*intent = Intent(this, RecycleBinActivity::class.java)
                startActivity(intent)*/

                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
            R.id.menuProperties -> {
                /*intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)*/

                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
            R.id.test1 ->{ /*try {
                Profit.syncAllProfits(this)
            } catch (e: Exception) {
                Toast.makeText(
                    baseContext, "test1 error",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
            R.id.test2 -> {/*try {
                Purchase.syncAllPurchases(this)
            } catch (e: Exception) {
                Toast.makeText(
                    baseContext, "test2 error",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
            R.id.menuAuthExit -> {
                /*val builder3 = AlertDialog.Builder(this)
                builder3.setTitle(getString(R.string.exit))
                builder3.setMessage(getString(R.string.exitMessage))
                //.setIcon(R.drawable.ic_launcher_cat)
                builder3.setPositiveButton(
                    getString(R.string.yesItIs)
                ) { dialog, id ->
                    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    mAuth.signOut()
                    val user: FirebaseUser = mAuth.getCurrentUser()
                    if (user == null) {
                        Preferences.deletePreferences(applicationContext)
                        DatabaseHelper.clearAllTables(applicationContext)
                        val intent = Intent(applicationContext, AuthActivity::class.java)
                        startActivity(intent)
                    }
                    dialog.cancel()
                }
                builder3.setNegativeButton(
                    getString(R.string.cancelButton)
                ) { dialog, id -> dialog.cancel() }
                builder3.show()
                return true*/
                intent = Intent(this, StubActivity::class.java)
                startActivity(intent)
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun menuButton(view: View?) {
        //Period.closePeriod(this);
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.openDrawer(Gravity.LEFT) //Edit Gravity.START need API 14
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

    fun setStatus( statusId: Int) {
        var _statusId = statusId
        val v_status_1 = findViewById<View>(R.id.mainStatus1)
        val v_status_2 = findViewById<View>(R.id.mainStatus2)
        val v_status_3 = findViewById<View>(R.id.mainStatus3)
        v_status_1.background = ContextCompat.getDrawable(this, R.drawable.ic_status_plan)
        v_status_2.background = ContextCompat.getDrawable(this, R.drawable.ic_status_accrued)
        v_status_3.background = ContextCompat.getDrawable(this, R.drawable.ic_status_baught)
        if (currentFilterStatus == statusId) {
            _statusId = 0
        } else {
            when (_statusId) {
                100 -> {
                    v_status_1.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_status_plan_active)
                }
                200 -> {
                    v_status_2.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_status_accrued_active)
                }
                300 -> {
                    v_status_3.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_status_baught_active)
                }
            }
        }
        currentFilterStatus = _statusId
        vm.getPurchases(periodId = period.id,
                        statusId = _statusId)
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

    /******* Buttons  */
    fun addPurchase(view: View?) {
        val intent = Intent(this@MainActivity, PurchaseActivity::class.java)
        intent.putExtra("EXTRA_PURCHASE_ID", 0)
        intent.putExtra("EXTRA_PARENT_ID", 0)
        intent.putExtra("EXTRA_PERIOD_ID", period.id)
        startActivity(intent)
    }

    fun gotoProfits(view: View?) {
        val intent = Intent(this, ProfitsActivity::class.java)
        startActivity(intent)
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

    //***** ! Buttons **********/
    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) //hide left menu
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                Toast.makeText(
                    baseContext, "Press once again to exit!",
                    Toast.LENGTH_SHORT
                ).show()
                back_pressed = System.currentTimeMillis()
            }
        }
    }

}