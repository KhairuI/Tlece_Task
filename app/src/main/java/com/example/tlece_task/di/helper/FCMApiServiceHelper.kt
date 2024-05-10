package com.example.tlece_task.di.helper

import com.example.tlece_task.network.ApiEndPoint
import com.example.tlece_task.network.FCMApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class FCMApiServiceHelper @Inject constructor() {

    operator fun invoke(): FCMApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiEndPoint.FCM_URL)
            .client(client())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(FCMApiService::class.java)
    }

    private fun client(): OkHttpClient = with(OkHttpClient().newBuilder()) {
        addInterceptor { chain ->
            val modifiedRequest = chain.request().newBuilder()
                .addHeader(ApiEndPoint.AUTHORIZATION, ApiEndPoint.AUTHORIZATION_VALUE)
                .addHeader(ApiEndPoint.CONTENT_TYPE, ApiEndPoint.APPLICATION_JSON).build()
            chain.proceed(modifiedRequest)
        }
        build()
    }
}