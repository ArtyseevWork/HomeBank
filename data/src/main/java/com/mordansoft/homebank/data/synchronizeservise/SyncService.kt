package com.mordansoft.homebank.data.synchronizeservise

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mordansoft.homebank.data.repo.PeriodRepoImpl
import com.mordansoft.homebank.data.repo.PreferencesRepoImpl
import com.mordansoft.homebank.data.repo.ProfitRepoImpl
import com.mordansoft.homebank.data.repo.PurchaseRepoImpl
import com.mordansoft.homebank.data.storage.AppDatabase
import com.mordansoft.homebank.data.storage.PreferencesStorageImplSnPr
import com.mordansoft.homebank.data.storage.firebase.*
import com.mordansoft.homebank.domain.usecase.period.PeriodSyncUc
import com.mordansoft.homebank.domain.usecase.preferences.PreferencesSyncUc
import com.mordansoft.homebank.domain.usecase.profit.ProfitSyncUc
import com.mordansoft.homebank.domain.usecase.purchase.PurchaseSyncUc



class SyncService : Service() {

    private lateinit var purchaseQuery: Query
    private lateinit var profitQuery: Query
    private lateinit var preferencesQuery: Query
    private lateinit var periodQuery: Query
    
    
    private var context: Context = this

    private val appDatabase = AppDatabase.getDatabase(context)

    private val profitDao = appDatabase.profitDao()
    private val profitRepo = ProfitRepoImpl(profitDao)
    private val profitSyncUc : ProfitSyncUc = ProfitSyncUc(profitRepo)

    private val preferencesRepo = PreferencesRepoImpl(PreferencesStorageImplSnPr(context))
    private val preferencesSyncUc : PreferencesSyncUc = PreferencesSyncUc(preferencesRepo)

    private val purchaseDao = appDatabase.purchaseDao()
    private val purchaseRepo = PurchaseRepoImpl(purchaseDao)
    private val purchaseSyncUc : PurchaseSyncUc = PurchaseSyncUc(purchaseRepo)

    private val periodDao = appDatabase.periodDao()
    private val periodRepo = PeriodRepoImpl(periodDao)
    private val periodSyncUc : PeriodSyncUc = PeriodSyncUc(periodRepo)

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        toast("service started")
        val userId =  FdbStorageImpl.getUserId()
            if (userId != null){
                profitQuery = FdbStorageImpl.getReference(FdbStorageImpl.Folders.Profit)
                profitQuery.addChildEventListener(ChildProfitListener(profitSyncUc))

                purchaseQuery = FdbStorageImpl.getReference(FdbStorageImpl.Folders.Purchase)
                purchaseQuery.addChildEventListener(ChildPurchaseListener(purchaseSyncUc))

                periodQuery = FdbStorageImpl.getReference(FdbStorageImpl.Folders.Period)
                periodQuery.addChildEventListener(ChildPeriodListener(periodSyncUc))

                preferencesQuery = FdbStorageImpl.getReference(FdbStorageImpl.Folders.Preferences)
                preferencesQuery.addChildEventListener(ChildPreferencesListener(preferencesSyncUc))
            }
        
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        toast("Service destroyed.")
    }


    fun Context.toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}





/*override fun onBind(intent: Intent): IBinder {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")

    myRef.setValue("Hello, World!")


    // Read from the database
    myRef.addValueEventListener(object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = snapshot.getValue()
            Log.d(TAG, "Value is: " + value)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }

    })
}

// Read from the database*/

