package com.mordansoft.homebank.app

import android.app.Application
import com.mordansoft.homebank.di.AppComponent
import com.mordansoft.homebank.di.AppModule
import com.mordansoft.homebank.di.DaggerAppComponent


class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()


        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getActualPeriod(): Int {
        return 1
    }


}