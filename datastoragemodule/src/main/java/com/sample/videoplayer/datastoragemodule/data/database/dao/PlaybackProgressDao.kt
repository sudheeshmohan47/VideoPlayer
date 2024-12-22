package com.sample.videoplayer.datastoragemodule.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.videoplayer.datastoragemodule.data.database.model.PlaybackProgress

@Dao
interface PlaybackProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdatePlaybackProgress(item: PlaybackProgress)

    @Query("SELECT * FROM playback_progress WHERE url = :url")
    suspend fun getPlaybackProgress(url: String): PlaybackProgress?
}
