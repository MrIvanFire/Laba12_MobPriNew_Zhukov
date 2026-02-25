package com.example.laba12_mobprinew_zhukov.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.laba12_mobprinew_zhukov.data.local.dao.NoteDao
import com.example.laba12_mobprinew_zhukov.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}