package com.mordansoft.homebank.data.storage


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mordansoft.homebank.data.model.ProfitD
import com.mordansoft.homebank.data.model.PurchaseD

@Database(entities = [PurchaseD::class, ProfitD::class ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao
    abstract fun profitDao(): ProfitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        //.createFromAsset("database/user.db")
                        .build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}


