package com.mordansoft.homebank.di

import android.content.Context
import com.mordansoft.homebank.app.App
import com.mordansoft.homebank.data.repo.PeriodRepoImpl
import com.mordansoft.homebank.data.repo.PreferencesRepoImpl
import com.mordansoft.homebank.data.repo.ProfitRepoImpl
import com.mordansoft.homebank.data.repo.PurchaseRepoImpl
import com.mordansoft.homebank.data.storage.*
import com.mordansoft.homebank.domain.repo.PeriodRepo
import com.mordansoft.homebank.domain.repo.PreferencesRepo
import com.mordansoft.homebank.domain.repo.ProfitRepo
import com.mordansoft.homebank.domain.repo.PurchaseRepo
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
    /**** ! Repositories *******/


    @Provides
    fun provideAppDatabase(context: Context): AppDatabase{
        return AppDatabase.getDatabase(context)
    }

}
