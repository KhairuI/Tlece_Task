package com.example.tlece_task.ui.datasource

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tlece_task.R
import com.example.tlece_task.base.helper.NetworkHandlerDataSource
import com.example.tlece_task.database.VideoRepo
import com.example.tlece_task.di.IoDispatcher
import com.example.tlece_task.model.NotificationModel
import com.example.tlece_task.model.NotificationRequest
import com.example.tlece_task.model.NotificationResponse
import com.example.tlece_task.model.VideoModel
import com.example.tlece_task.network.ApiService
import com.example.tlece_task.network.FCMApiService
import com.example.tlece_task.utils.DataSourceUtils
import com.example.tlece_task.utils.Result
import com.example.tlece_task.utils.UiText
import com.example.tlece_task.utils.extension.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.time.delay
import java.time.Duration
import javax.inject.Inject

interface VideoDataSource {
    suspend fun getVideoList(): Flow<Result<VideoModel>>
    suspend fun sendNotification(): Flow<Result<NotificationResponse>>
}

class VideoDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val videoRepo: VideoRepo,
    private val apiService: ApiService,
    private val fcmApiService: FCMApiService,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : VideoDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getVideoList(): Flow<Result<VideoModel>> = flow {
        var isFirstTime = true

        while (currentCoroutineContext().isActive) {

            try {
                if (isFirstTime) emit(Result.Loading())

                if (!hasInternetConnection()) {
                    emit(Result.Error(UiText.StringResource(R.string.no_internet)))
                    return@flow
                }

                val response = apiService.videoApiCall()
                if (!response.isSuccessful) {
                    emit(Result.Error(UiText.DynamicString(response.errorBody().toString())))
                    return@flow

                }

                response.body()?.let {
                    videoRepo.insert(it)
                    if (isFirstTime) emit(Result.Success(it))
                }


            } catch (e: Exception) {
                emit(Result.Error(networkHandlerDataSource.handleException(e)))
            } finally {
                emit(Result.Loading(false))
            }

            isFirstTime = false
            delay(Duration.ofMinutes(5))
        }

    }.flowOn(ioDispatcher)

    override suspend fun sendNotification(): Flow<Result<NotificationResponse>> = flow {
        try {
            emit(Result.Loading())

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.no_internet)))
                return@flow
            }

            val request = NotificationRequest(
                to = DataSourceUtils.getToken(context),
                notification = NotificationModel(
                    body = "Hi there, greetings from Tlece",
                    title = "Tlece"
                )
            )

            val response = fcmApiService.sendNotification(request)
            if (!response.isSuccessful) {
                emit(Result.Error(UiText.DynamicString(response.errorBody().toString())))
                return@flow
            }

            response.body()?.let { data ->
                emit(Result.Success(data))
            }
        } catch (e: Exception) {
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

}