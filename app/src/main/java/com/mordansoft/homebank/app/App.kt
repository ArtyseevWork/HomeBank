package com.mordansoft.homebank.app

import android.app.Application
import androidx.room.Room
import com.mordansoft.homebank.data.storage.AppDatabase


class App : Application() {

    var instance: App? = null
    private var database: AppDatabase? = null


    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder<AppDatabase>(this, AppDatabase::class.java, "database")
            .build()

    }


   /* fun getInstance(): App? {
        return instance
    }

    fun getDatabase(): AppDatabase? {
        return database
    }
*/
}