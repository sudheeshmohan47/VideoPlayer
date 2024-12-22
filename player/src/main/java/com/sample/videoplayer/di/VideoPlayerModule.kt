package com.sample.videoplayer.di

import androidx.work.WorkManager
import com.sample.videoplayer.data.VideoPlayerService
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.data.database.dao.PlaybackProgressDao
import com.sample.videoplayer.domain.VideoPlayerRepository
import com.sample.videoplayer.domain.VideoPlayerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class VideoPlayerModule {
    @Provides
    fun provideVideoPlayerUseCase(videoPlayerRepository: VideoPlayerRepository): VideoPlayerUseCase {
        return VideoPlayerUseCase(videoPlayerRepository)
    }

    @Provides
    fun provideVideoPlayerRepository(
        downloadedFilesDao: DownloadedFilesDao,
        playbackProgressDao: PlaybackProgressDao,
        workManager: WorkManager
    ): VideoPlayerRepository {
        return VideoPlayerService(
            workManager,
            downloadedFilesDao,
            playbackProgressDao
        )
    }
}
