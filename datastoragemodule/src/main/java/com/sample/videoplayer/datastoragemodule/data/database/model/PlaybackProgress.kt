package com.sample.videoplayer.datastoragemodule.data.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "playback_progress",
    indices = [Index(value = ["url"], unique = true)]
)
data class PlaybackProgress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val progress: Long
)
