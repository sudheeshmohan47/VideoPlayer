package com.sample.videoplayer.domain

interface VideoPlayerRepository {
    suspend fun getMediaDownloadStatus(videoUrlOrPath: String): String
    suspend fun getPlaybackProgress(videoUrlOrPath: String): Long
    suspend fun updatePlaybackProgress(videoUrlOrPath: String, progress: Long)
    suspend fun updateMediaDownloadStatus(videoUrlOrPath: String, title: String, downloadStatus: String)
}
