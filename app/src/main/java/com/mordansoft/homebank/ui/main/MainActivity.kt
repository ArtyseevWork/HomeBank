package com.mordansoft.homebank.ui.main

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
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
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.ui.StubActivity
import com.mordansoft.homebank.ui.profits.ProfitsActivity
import com.mordansoft.homebank.ui.purchase.PurchaseActivity


class MainActivity : AppCompatActivity() {
    private var periodId = 0
    protected lateinit var mMyApp: Application
    private var listPurchases: ArrayList<Purchase> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private var filterStatus = 0
    private lateinit var adapter: PurchaseAdapter
    private lateinit var vm : MainViewModel

    @javax.inject.Inject
    lateinit var vmFactory: MainViewModelFactory
    //var purchaseEventListener: ChildEventListener? = null
    //var purchasesQuery: Query? = null
    //var profitsEventListener: ChildEventListener? = null
    //var profitsQuery: Query? = null
    //var onlineMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main_new)
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]

        vm.getPurchases();
        //vm.getMainPurchasesMutableLiveData().observe(this,mainPurchasesObserver)
        vm.purchases.observe(this,mainPurchasesObserver)
        //createRecyclerView(R.id.mainPurchasesRecyclerView)



        //periodId = mMyApp.getCurrentPeriod()
        //onlineMode = mMyApp.getOnlineMode()
        //val profitsMode: Int = mMyApp.getProfitsMode()
        //var periodBudgetPlan = 0f
        //var periodBudgetFact = 0f
        /*if (profitsMode == 0) {
            periodBudgetPlan = Profit.getPeriodProfits(this, periodId - profitsMode, 100, true)
            periodBudgetFact = Profit.getPeriodProfits(this, periodId - profitsMode, 200, false)
            periodBudgetFact += Profit.getPeriodProfits(this, periodId - profitsMode, 300, false)
        } else if (profitsMode == 1) {
            periodBudgetPlan = Profit.getPeriodProfits(this, periodId - profitsMode, 200, false)
            periodBudgetPlan += Profit.getPeriodProfits(this, periodId - profitsMode, 300, false)
            periodBudgetFact = periodBudgetPlan
        }
        val spentPlan: Float = Purchase.getPeriodSpending(this, periodId, false)
        val spentFact: Float = Purchase.getPeriodSpending(this, periodId, true)
        val balancePlan = periodBudgetPlan - spentPlan
        val balanceFact = periodBudgetFact - spentFact
        val v_plan_budget = findViewById<TextView>(R.id.activity_main__plan_budget)
        val v_fact_budget = findViewById<TextView>(R.id.activity_main__fact_budget)
        val v_plan_spent = findViewById<TextView>(R.id.activity_main__plan_spent)
        val v_fact_spent = findViewById<TextView>(R.id.activity_main__fact_spent)
        val v_plan_balance = findViewById<TextView>(R.id.activity_main__plan_balance)
        val v_fact_balance = findViewById<TextView>(R.id.activity_main__fact_balance)
        v_plan_budget.text = String.format("%.0f", periodBudgetPlan)
        v_fact_budget.text = String.format("%.0f", periodBudgetFact)
        v_plan_spent.text = String.format("%.0f", spentPlan)
        v_fact_spent.text = String.format("%.0f", spentFact)
        v_plan_balance.text = String.format("%.0f", balancePlan)
        v_fact_balance.text = String.format("%.0f", balanceFact)
        val tvPeriodNow = findViewById<TextView>(R.id.mainPeriod)
        tvPeriodNow.setText(mMyApp.getCurrentPeriodName())
        val tvActiveStatus = findViewById<TextView>(R.id.mainActiveStatus)
        if (periodId == mMyApp.getActualPeriod()) {
            tvActiveStatus.text = getString(R.string.active)
        } else {
            tvActiveStatus.text = getString(R.string.notActive)
        }
        buttonsPeriodAvailable()
        val query = "parent_id = " + "\"" + "main" + "\"" + " order by timestamp desc"*/



        //listPurchases = Purchase.getPurchasesFromDatabase(this, query, periodId)

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

    private val mainPurchasesObserver: Observer<ArrayList<Purchase>> =
        Observer<ArrayList<Purchase>> { newMainPurchases -> // Update the UI, in this case, a TextView.
            listPurchases = newMainPurchases
            createRecyclerView()
        }


    /*protected fun createFdbListeners() {
        if (onlineMode) {
            purchaseEventListener = ChildEventListeners.getPurchaseEventListener(this)
            purchasesQuery = FirebaseHelper.purchasesSync(purchaseEventListener)
            profitsEventListener = ChildEventListeners.getProfitEventListener(this)
            profitsQuery = FirebaseHelper.profitsSync(profitsEventListener)
        }
    }

    protected fun removeFdbListeners() {
        if (onlineMode) {
            purchasesQuery.removeEventListener(purchaseEventListener)
            profitsQuery.removeEventListener(profitsEventListener)
        }
    }*/

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

    //****** ! left menu **********/
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
        var statusId = statusId
        val v_status_1 = findViewById<View>(R.id.mainStatus1)
        val v_status_2 = findViewById<View>(R.id.mainStatus2)
        val v_status_3 = findViewById<View>(R.id.mainStatus3)
        v_status_1.background = ContextCompat.getDrawable(this, R.drawable.ic_status_plan)
        v_status_2.background = ContextCompat.getDrawable(this, R.drawable.ic_status_accrued)
        v_status_3.background = ContextCompat.getDrawable(this, R.drawable.ic_status_baught)
        var query = "parent_id = " + "\"" + "main" + "\"" + " order by timestamp desc"
        if (filterStatus == statusId) {
            statusId = 0
        } else {
            when (statusId) {
                100 -> {
                    v_status_1.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_status_plan_active)
                    query = "status_id = $statusId and parent_id = \"main\" order by timestamp desc"
                }
                200 -> {
                    v_status_2.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_status_accrued_active)
                    query = "status_id = $statusId and parent_id = \"main\" order by timestamp desc"
                }
                300 -> {
                    v_status_3.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_status_baught_active)
                    query = "status_id = $statusId and parent_id = \"main\" order by timestamp desc"
                }
            }
        }
        filterStatus = statusId
        //mMyApp.setStatusInMainActivity(statusId)
        /*listPurchases = Purchase.getPurchasesFromDatabase(this, query, periodId)
        adapter = PurchaseAdapter(listPurchases, this)
        recyclerView!!.adapter = adapter
        adapter.setListener { purchaseId ->
            val intent = Intent(this@MainActivity, PurchaseActivity::class.java)
            intent.putExtra("EXTRA_PURCHASE_ID", purchaseId)
            startActivity(intent)
        }*/
    }

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

    /******* Buttons  */
    /*fun addPurchase(view: View?) {
        val intent = Intent(this@MainActivity, PurchaseActivity::class.java)
        intent.putExtra("EXTRA_PURCHASE_ID", "0")
        intent.putExtra("EXTRA_PARENT_ID", "main")
        startActivity(intent)
    }*/

    fun gotoProfits(view: View?) {
        val intent = Intent(this, ProfitsActivity::class.java)
        startActivity(intent)
    }
    /*

    fun previousPeriod(view: View?) {
        changePeriod(-1)
    }

    fun nextPeriod(view: View?) {
        changePeriod(+1)
    }*/

    /*private fun changePeriod(position: Int) {
        if (Period.neighborIsAvailable(this, periodId, position)) {
            val intent = Intent(this, MainActivity::class.java)
            mMyApp.setCurrentPeriod(periodId + position)
            startActivity(intent)
        }
    }*/

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

    /*private fun clearReferences() {
        val currActivity: Activity = mMyApp.getCurrentActivity()
        if (this == currActivity) mMyApp.setCurrentActivity(null)
    }*/

    companion object {
        private var back_pressed: Long = 0
    }
}