package com.sample.videoplayer.data

import androidx.work.WorkManager
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.data.database.dao.PlaybackProgressDao
import com.sample.videoplayer.datastoragemodule.data.database.model.DownloadedFiles
import com.sample.videoplayer.datastoragemodule.data.database.model.PlaybackProgress
import com.sample.videoplayer.datastoragemodule.data.workmanager.DownloadWorkManager
import com.sample.videoplayer.domain.VideoPlayerRepository

class VideoPlayerService(
    private val workManager: WorkManager,
    private val downloadedFilesDao: DownloadedFilesDao,
    private val playbackProgressDao: PlaybackProgressDao
) : VideoPlayerRepository {

    override suspend fun getMediaDownloadStatus(videoUrlOrPath: String): String {
        val downloadedFiles = downloadedFilesDao.getDownloadedFiles()
        return downloadedFiles.find { it.url == videoUrlOrPath || it.localPath == videoUrlOrPath }?.downloadStatus
            ?: "NOT_DOWNLOADED"
    }

    override suspend fun getPlaybackProgress(videoUrlOrPath: String): Long {
        return playbackProgressDao.getPlaybackProgress(videoUrlOrPath)?.progress ?: 0L
    }

    override suspend fun updatePlaybackProgress(videoUrlOrPath: String, progress: Long) {
        playbackProgressDao.addOrUpdatePlaybackProgress(
            PlaybackProgress(
                url = videoUrlOrPath,
                progress = progress
            )
        )
    }

    override suspend fun updateMediaDownloadStatus(
        videoUrlOrPath: String,
        title: String,
        downloadStatus: String
    ) {
        downloadedFilesDao.addDownloadedFile(
            DownloadedFiles(
                title = title,
                url = videoUrlOrPath,
                downloadStatus = downloadStatus
            )
        )

        // Schedule Download work for download non downloaded files
        scheduleDownloadWork()
    }

    private fun scheduleDownloadWork() {
        workManager.enqueue(DownloadWorkManager.oneTimeWorkRequest())
    }
}
