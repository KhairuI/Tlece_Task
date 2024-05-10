package com.example.tlece_task.ui.delegate

import com.example.tlece_task.di.ApplicationScope
import com.example.tlece_task.model.NotificationResponse
import com.example.tlece_task.model.VideoModel
import com.example.tlece_task.ui.datasource.VideoDataSource
import com.example.tlece_task.utils.Result
import com.example.tlece_task.utils.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface VideoViewModelDelegate {
    val videoListResponse: Flow<Result<VideoModel>>
    fun getVideoList()
    fun sendNotification()
    val sendNotificationResponse: Flow<Result<NotificationResponse>>
}

internal class VideoViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val videoDataSource: VideoDataSource
) : VideoViewModelDelegate {

    private val _videoListResponse = Channel<Result<VideoModel>>(Channel.CONFLATED)
    override val videoListResponse = _videoListResponse.receiveAsFlow()

    private val _sendNotificationResponse = Channel<Result<NotificationResponse>>(Channel.CONFLATED)
    override val sendNotificationResponse = _sendNotificationResponse.receiveAsFlow()


    override fun getVideoList() {
        applicationScope.launch {
            videoDataSource.getVideoList().collect { result ->
                _videoListResponse.tryOffer(result)
            }
        }
    }

    override fun sendNotification() {
        applicationScope.launch {
            videoDataSource.sendNotification().collect { result ->
                _sendNotificationResponse.tryOffer(result)
            }
        }
    }
}
