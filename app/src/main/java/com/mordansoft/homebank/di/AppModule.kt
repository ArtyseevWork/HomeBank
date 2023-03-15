package com.mordansoft.homebank.di

import android.content.Context
import com.mordansoft.homebank.domain.usecase.GetMainPurchasesUc
import com.mordansoft.homebank.ui.main.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext() : Context{
        return context
    }
    @Provides
    fun provideMainViewModelFactory(getMainPurchasesUc: GetMainPurchasesUc) : MainViewModelFactory{
        return MainViewModelFactory(getMainPurchasesUc)
    }


}