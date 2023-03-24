package com.mordansoft.homebank.di
import com.mordansoft.homebank.ui.exchange_rates.ExchangeRatesActivity
import com.mordansoft.homebank.ui.main.MainActivity
import com.mordansoft.homebank.ui.profit.ProfitActivity
import com.mordansoft.homebank.ui.profits.ProfitsActivity
import com.mordansoft.homebank.ui.purchase.PurchaseActivity
import dagger.Component

@Component(modules = [ DomainModule::class, AppModule::class, DataModule::class])
interface AppComponent  {

    fun inject(mainActivity: MainActivity)
    fun inject(purchaseActivity: PurchaseActivity)
    fun inject(profitsActivity: ProfitsActivity)
    fun inject(profitActivity: ProfitActivity)
    fun inject(exchangeRatesActivity: ExchangeRatesActivity)
}