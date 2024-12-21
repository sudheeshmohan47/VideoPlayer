package com.sample.videoplayer

import android.app.Application
//import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

//@HiltAndroidApp
class VideoPlayerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
       /* if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }*/
    }
}
