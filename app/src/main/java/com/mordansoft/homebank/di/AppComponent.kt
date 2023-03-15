package com.mordansoft.homebank.di
import com.mordansoft.homebank.ui.main.MainActivity
import dagger.Component
import javax.inject.Inject

@Component(modules = [ DomainModule::class, AppModule::class, DataModule::class])
interface AppComponent  {

    fun inject(mainActivity: MainActivity)
}