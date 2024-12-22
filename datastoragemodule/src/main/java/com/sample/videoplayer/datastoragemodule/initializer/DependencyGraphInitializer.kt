package com.sample.videoplayer.datastoragemodule.initializer

import android.content.Context
import androidx.startup.Initializer
import com.sample.videoplayer.datastoragemodule.di.InitializerEntryPoint

// This is used for using Hilt with Work manager
// Credits: https://medium.com/@santimattius/workmanager-with-hilt-and-app-startup-80b34062e144
class DependencyGraphInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
