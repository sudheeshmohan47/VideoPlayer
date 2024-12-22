package com.sample.videoplayer.datastoragemodule.di

import android.content.Context
import androidx.room.Room
import com.sample.videoplayer.datastoragemodule.data.database.VideoPlayerDatabase
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.data.database.dao.PlaybackProgressDao
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
    fun provideDatabase(@ApplicationContext context: Context): VideoPlayerDatabase {
        return Room.databaseBuilder(context, VideoPlayerDatabase::class.java, "video_player_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDownloadedFilesDao(database: VideoPlayerDatabase): DownloadedFilesDao {
        return database.downloadedFilesDao()
    }

    @Provides
    @Singleton
    fun providePlaybackProgressDao(database: VideoPlayerDatabase): PlaybackProgressDao {
        return database.playbackProgressDao()
    }
}
