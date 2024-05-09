package com.example.tlece_task.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tlece_task.model.VideoModelItem

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(video: VideoModelItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: List<VideoModelItem>)

    @Query("SELECT * FROM videos")
    fun load(): List<VideoModelItem>
}