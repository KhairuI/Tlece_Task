package com.example.tlece_task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tlece_task.model.VideoModelItem
import com.example.tlece_task.utils.AppConstants

@Database(
    entities = [
        (VideoModelItem::class)
    ],
    version = AppConstants.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao
}