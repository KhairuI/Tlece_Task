package com.example.tlece_task.di

import com.example.tlece_task.di.helper.ApiServiceHelper
import com.example.tlece_task.di.helper.FCMApiServiceHelper
import com.example.tlece_task.network.ApiService
import com.example.tlece_task.network.FCMApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideApiService(): ApiService = ApiServiceHelper().invoke()

    @Provides
    @Singleton
    internal fun provideFCMApiService(): FCMApiService = FCMApiServiceHelper().invoke()
}