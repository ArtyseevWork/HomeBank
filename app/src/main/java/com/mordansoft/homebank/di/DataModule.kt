package com.mordansoft.homebank.di

import android.content.Context
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.data.repo.PurchaseRepoImpl
import com.mordansoft.homebank.data.storage.AppDatabase
import com.mordansoft.homebank.data.storage.PurchaseDao
import com.mordansoft.homebank.domain.repo.PurchaseRepo
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePurchaseRepo(purchaseDao: PurchaseDao) : PurchaseRepo {
        return PurchaseRepoImpl(purchaseDao)
    }
    @Provides
    fun providePurchaseDao(appDatabase: AppDatabase) : PurchaseDao {
        return appDatabase.purchaseDao()
    }

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase{
        return AppDatabase.getDatabase(context)
    }

}
