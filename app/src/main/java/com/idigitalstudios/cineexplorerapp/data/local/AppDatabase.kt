package com.idigitalstudios.cineexplorerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.idigitalstudios.cineexplorerapp.data.local.entity.ShowEntity

@Database(entities = [ShowEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
}
