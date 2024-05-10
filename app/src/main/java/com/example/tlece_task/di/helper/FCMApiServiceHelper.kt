package com.example.tlece_task.di.helper

import com.example.tlece_task.BuildConfig
import com.example.tlece_task.network.ApiEndPoint
import com.example.tlece_task.network.ApiService
import com.example.tlece_task.network.FCMApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class FCMApiServiceHelper @Inject constructor() {

    operator fun invoke(): FCMApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiEndPoint.FCM_URL)
            .client(
                OkHttpClient.Builder().also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor().also {
                            it.level = HttpLoggingInterceptor.Level.BODY
                        }
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(FCMApiService::class.java)
    }
}