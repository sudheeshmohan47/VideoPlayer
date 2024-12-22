package com.sample.videoplayer.datastoragemodule.data.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "downloaded_files",
    indices = [Index(value = ["url"], unique = true)]
)
data class DownloadedFiles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val url: String,
    val localPath: String = "",
    val downloadStatus: String,
    val description: String = "",
    val thumbnailUrl: String = ""
)
