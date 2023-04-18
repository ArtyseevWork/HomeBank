package com.mordansoft.homebank.di

import android.content.Context
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.data.repo.*
import com.mordansoft.homebank.data.storage.*
import com.mordansoft.homebank.data.storage.firebase.ChildPeriodListener
import com.mordansoft.homebank.data.storage.firebase.ChildPreferencesListener
import com.mordansoft.homebank.data.storage.firebase.ChildProfitListener
import com.mordansoft.homebank.data.storage.firebase.ChildPurchaseListener
import com.mordansoft.homebank.domain.repo.*
import com.mordansoft.homebank.domain.usecase.period.PeriodSyncUc
import com.mordansoft.homebank.domain.usecase.preferences.PreferencesSyncUc
import com.mordansoft.homebank.domain.usecase.profit.ProfitSyncUc
import com.mordansoft.homebank.domain.usecase.purchase.PurchaseSyncUc
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    /****** Storage implementations (Dao's) *******/
    @Provides
    fun providePreferencesStorageImplSnPr(context: Context) : PreferencesStorageImplSnPr {
        return PreferencesStorageImplSnPr(context)
    }

    @Provides
    fun provideProfitDao(appDatabase: AppDatabase) : ProfitDao {
        return appDatabase.profitDao()
    }
    @Provides
    fun providePurchaseDao(appDatabase: AppDatabase) : PurchaseDao {
        return appDatabase.purchaseDao()
    }

    @Provides
    fun providePeriodDao(appDatabase: AppDatabase) : PeriodDao {
        return appDatabase.periodDao()
    }
    /**** ! Storage implementations (Dao's) *******/


    /****** Repositories *******/
    @Provides
    fun providePurchaseRepo(purchaseDao: PurchaseDao) : PurchaseRepo {
        return PurchaseRepoImpl(purchaseDao)
    }

    @Provides
    fun provideProfitRepo(profitDao: ProfitDao) : ProfitRepo{
        return ProfitRepoImpl(profitDao)
    }

    @Provides
    fun providePreferencesRepo(preferencesStorageImplSnPr: PreferencesStorageImplSnPr)
            : PreferencesRepo {
        return PreferencesRepoImpl(preferencesStorageImplSnPr)
    }
    @Provides
    fun providePeriodRepo(periodDao: PeriodDao) : PeriodRepo{
        return PeriodRepoImpl(periodDao)
    }
    @Provides
    fun provideExchangeRepo() : ExchangeRepo {
        return ExchangeRepoImpl()
    }

    /**** ! Repositories *******/


    @Provides
    fun provideAppDatabase(context: Context): AppDatabase{
        return AppDatabase.getDatabase(context)
    }


    @Provides
    fun provideChildPurchaseListener(purchaseSyncUc : PurchaseSyncUc) : ChildPurchaseListener {
        return ChildPurchaseListener(purchaseSyncUc = purchaseSyncUc)
    }

    @Provides
    fun provideChildProfitListener(profitSyncUc : ProfitSyncUc) : ChildProfitListener {
        return ChildProfitListener(profitSyncUc = profitSyncUc)
    }
    @Provides
    fun provideChildPeriodListener(periodSyncUc : PeriodSyncUc) : ChildPeriodListener {
        return ChildPeriodListener(periodSyncUc = periodSyncUc)
    }
    @Provides
    fun provideChildPreferencesListener(preferencesSyncUc : PreferencesSyncUc) : ChildPreferencesListener {
        return ChildPreferencesListener(preferencesSyncUc = preferencesSyncUc)
    }



}
