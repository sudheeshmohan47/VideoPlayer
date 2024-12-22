package com.sample.videoplayer.datastoragemodule.di

import android.content.Context
import com.sample.videoplayer.datastoragemodule.initializer.DownloadedFilesDaoInitializer
import com.sample.videoplayer.datastoragemodule.initializer.WorkManagerInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

// This interface is used for using Hilt with Work manager
@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {
    fun inject(crashTrackerInitializer: DownloadedFilesDaoInitializer)
    fun inject(workManagerInitializer: WorkManagerInitializer)

    companion object {
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }
}
