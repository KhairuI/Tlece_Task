package com.example.tlece_task.database

import com.example.tlece_task.model.VideoModelItem

interface VideoRepo {

    suspend fun insert(video: VideoModelItem)

    suspend fun insert(list: List<VideoModelItem>)

    fun load(): List<VideoModelItem>
}