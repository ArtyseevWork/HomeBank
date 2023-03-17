package com.mordansoft.homebank.di
import com.mordansoft.homebank.ui.main.MainActivity
import com.mordansoft.homebank.ui.purchase.PurchaseActivity
import dagger.Component
import javax.inject.Inject

@Component(modules = [ DomainModule::class, AppModule::class, DataModule::class])
interface AppComponent  {

    fun inject(mainActivity: MainActivity)
    fun inject(purchaseActivity: PurchaseActivity)
}