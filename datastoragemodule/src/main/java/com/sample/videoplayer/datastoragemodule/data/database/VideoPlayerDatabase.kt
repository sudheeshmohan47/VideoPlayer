package com.sample.videoplayer.datastoragemodule.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.data.database.dao.PlaybackProgressDao
import com.sample.videoplayer.datastoragemodule.data.database.model.DownloadedFiles
import com.sample.videoplayer.datastoragemodule.data.database.model.PlaybackProgress

@Database(entities = [PlaybackProgress::class, DownloadedFiles::class], version = 1)
abstract class VideoPlayerDatabase : RoomDatabase() {
    abstract fun downloadedFilesDao(): DownloadedFilesDao
    abstract fun playbackProgressDao(): PlaybackProgressDao
}
