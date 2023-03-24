package com.mordansoft.homebank.ui.exchange_rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinexample.screens.start.CurrencyAdapter
import com.mordansoft.homebank.R
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.domain.model.Currency


class ExchangeRatesActivity : AppCompatActivity() {

    @javax.inject.Inject
    lateinit var vmFactory: ExchangeRatesViewModelFactory
    var currencyList : List<Currency> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rates)

        (applicationContext as App).appComponent.inject(this)

        val vm = ViewModelProvider(this, vmFactory)[ExchangeRatesViewModel::class.java]

        vm.getCurrencyList()
        vm.myCurrencyList.observe(this, currencyListObserver)

    }

    private fun updateUi(){
        createRecyclerView()
    }

    private val currencyListObserver: Observer<List<Currency>> =
        Observer<List<Currency>> { newCurrencyList ->
            currencyList = newCurrencyList
            updateUi()
        }

    fun createRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        linearLayoutManager.reverseLayout = false
        linearLayoutManager.stackFromEnd = false
        val recyclerView = findViewById<RecyclerView>(R.id.rv_exchange_rate)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = CurrencyAdapter(currencyList)
        recyclerView.adapter = adapter
    }

}