package com.example.laba12_mobprinew_zhukov

import android.app.Application
import androidx.room.Room
import com.example.laba12_mobprinew_zhukov.data.local.AppDatabase

class NotesApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "notes_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}