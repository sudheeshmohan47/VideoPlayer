package com.sample.videoplayer.domain

import com.sample.videoplayer.domain.model.DownloadStatus

class VideoPlayerUseCase(private val videoPlayerRepository: VideoPlayerRepository) {

    suspend fun getMediaDownloadStatus(videoUrlOrPath: String): DownloadStatus {
        val downloadStatusFromDb = videoPlayerRepository.getMediaDownloadStatus(videoUrlOrPath)
        return when (downloadStatusFromDb) {
            DownloadStatus.DOWNLOADED.name -> DownloadStatus.DOWNLOADED
            DownloadStatus.DOWNLOADING.name -> DownloadStatus.DOWNLOADING
            DownloadStatus.NOT_DOWNLOADED.name -> DownloadStatus.NOT_DOWNLOADED
            DownloadStatus.ERROR.name -> DownloadStatus.ERROR
            else -> DownloadStatus.NOT_DOWNLOADED
        }
    }

    suspend fun getPlaybackProgress(videoUrlOrPath: String): Long {
        return videoPlayerRepository.getPlaybackProgress(videoUrlOrPath)
    }

    suspend fun updateDownloadStatus(url: String, title: String, status: DownloadStatus) {
        videoPlayerRepository.updateMediaDownloadStatus(
            videoUrlOrPath = url,
            title = title,
            downloadStatus = DownloadStatus.DOWNLOADING.name
        )
    }
}
