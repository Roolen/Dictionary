package com.example.dictionary

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        reference = this
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database_4.db")
            .createFromAsset("database.db")
            .build()
    }

    companion object {
        private var reference: App? = null

        fun getInstance(): App {
            return reference ?: throw IllegalStateException("App is not initialized.")
        }
    }
}