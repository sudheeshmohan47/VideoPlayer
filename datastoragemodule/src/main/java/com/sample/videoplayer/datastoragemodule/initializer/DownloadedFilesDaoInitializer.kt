package com.sample.videoplayer.datastoragemodule.initializer

import android.content.Context
import androidx.startup.Initializer
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.di.InitializerEntryPoint
import javax.inject.Inject

/**
 * We are using DownloadedFilesDao in WorkManager. So we are initialising it for that here.
 */
class DownloadedFilesDaoInitializer : Initializer<DownloadedFilesDao> {

    @Inject
    lateinit var downloadedFilesDao: DownloadedFilesDao
    override fun create(context: Context): DownloadedFilesDao {
        InitializerEntryPoint.resolve(context).inject(this)
        return downloadedFilesDao
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}
