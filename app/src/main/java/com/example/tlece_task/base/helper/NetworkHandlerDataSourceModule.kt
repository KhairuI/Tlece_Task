package com.example.tlece_task.base.helper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkHandlerDataSourceModule {

    @Provides
    @Singleton
    internal fun provideNetworkHandlerDataSource(networkHandlerDataSourceImpl: NetworkHandlerDataSourceImpl): NetworkHandlerDataSource =
        networkHandlerDataSourceImpl
}