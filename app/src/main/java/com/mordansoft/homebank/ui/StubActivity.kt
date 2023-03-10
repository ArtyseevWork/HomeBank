package com.mordansoft.homebank.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mordansoft.homebank.R
import com.mordansoft.homebank.data.repo.PurchaseRepoImpl
import com.mordansoft.homebank.data.storage.AppDatabase
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class StubActivity : AppCompatActivity() {

    private lateinit var purchaseRepo: PurchaseRepo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stub)
        //var database: AppDatabase?  = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()

        purchaseRepo =
            PurchaseRepoImpl(AppDatabase.getDatabase(application).purchaseDao())

        CoroutineScope(IO).launch {
            test()
        }

    }
    suspend fun test(){
        purchaseRepo.insertTestPurchase()
    }
}