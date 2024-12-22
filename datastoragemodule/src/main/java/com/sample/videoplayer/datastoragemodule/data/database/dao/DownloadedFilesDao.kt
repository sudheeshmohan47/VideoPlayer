package com.sample.videoplayer.datastoragemodule.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sample.videoplayer.datastoragemodule.data.database.model.DownloadedFiles

@Dao
interface DownloadedFilesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDownloadedFile(item: DownloadedFiles)

    @Query("SELECT * FROM downloaded_files")
    suspend fun getDownloadedFiles(): List<DownloadedFiles>
}
