package com.example.tlece_task.network

import com.example.tlece_task.model.NotificationRequest
import com.example.tlece_task.model.NotificationResponse
import com.example.tlece_task.model.VideoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FCMApiService {

    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAlmLT_MQ:APA91bEaanY2-mK70fW4m-twwh3gZVyWX_nMsBWhKs8sAQI0-FPkL72Zy32So7Wv3txlC2qPBoW67x6r60qbjVi10SQ05ffyD5_OXtKQ2XbgMq8eN9BVMYBboOPvzaDbUWbJ2hCU0OXm"
    )
    @POST(ApiEndPoint.ENDPOINT_FCM)
    suspend fun sendNotification(@Body request: NotificationRequest): Response<NotificationResponse>
}