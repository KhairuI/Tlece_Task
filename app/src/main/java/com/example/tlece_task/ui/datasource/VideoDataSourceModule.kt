package com.example.tlece_task.ui.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object VideoDataSourceModule {

    @Provides
    @Singleton
    internal fun provideVideoDataSource(videoDataSourceImpl: VideoDataSourceImpl): VideoDataSource =
        videoDataSourceImpl
}