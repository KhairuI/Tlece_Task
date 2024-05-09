package com.example.tlece_task.database

import com.example.tlece_task.model.VideoModelItem
import javax.inject.Inject

class VideoRepository @Inject constructor(private val dao: VideoDao) : VideoRepo {

    override suspend fun insert(video: VideoModelItem) {
        dao.insert(video)
    }

    override suspend fun insert(list: List<VideoModelItem>) {
        dao.insert( list)
    }


    override fun load(): List<VideoModelItem> {
        return dao.load()
    }
}