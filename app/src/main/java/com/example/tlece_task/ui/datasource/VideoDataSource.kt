package com.example.tlece_task.ui.datasource

import android.content.Context
import com.example.tlece_task.R
import com.example.tlece_task.database.VideoRepo
import com.example.tlece_task.di.IoDispatcher
import com.example.tlece_task.model.VideoModel
import com.example.tlece_task.network.ApiService
import com.example.tlece_task.utils.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.tlece_task.utils.Result
import javax.inject.Inject

interface VideoDataSource {
    suspend fun getVideoList(): Flow<Result<VideoModel>>
}

class VideoDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val videoRepo: VideoRepo,
    private val apiService: ApiService
) : VideoDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun getVideoList(): Flow<Result<VideoModel>> = flow {
        try {
            emit(Result.Loading())

            if (!hasInternetConnection()) {
                emit(Result.Error(context.getString(R.string.no_internet)))
                return@flow
            }

            val response = apiService.videoApiCall()
            if (!response.isSuccessful) {
                emit(Result.Error(response.errorBody().toString()))
                return@flow

            }

            response.body()?.let {
                videoRepo.insert(it)
                emit(Result.Success(it))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)
}