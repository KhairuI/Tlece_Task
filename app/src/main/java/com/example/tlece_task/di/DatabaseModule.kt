package com.example.tlece_task.di

import android.content.Context
import androidx.room.Room
import com.example.tlece_task.database.AppDatabase
import com.example.tlece_task.database.VideoRepo
import com.example.tlece_task.database.VideoRepository
import com.example.tlece_task.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.DB_NAME)
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    @Singleton
    internal fun provideVideoRepoHelper(appDatabase: AppDatabase): VideoRepo =
        VideoRepository(appDatabase.videoDao())
}