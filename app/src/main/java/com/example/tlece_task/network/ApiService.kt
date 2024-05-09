package com.example.tlece_task.network

import com.example.tlece_task.model.VideoModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ApiEndPoint.ENDPOINT_VIDEO)
    suspend fun videoApiCall(): Response<VideoModel>
}