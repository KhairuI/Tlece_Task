package com.example.tlece_task.network

import com.example.tlece_task.model.NotificationRequest
import com.example.tlece_task.model.NotificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMApiService {

    @POST(ApiEndPoint.ENDPOINT_FCM)
    suspend fun sendNotification(@Body request: NotificationRequest): Response<NotificationResponse>
}