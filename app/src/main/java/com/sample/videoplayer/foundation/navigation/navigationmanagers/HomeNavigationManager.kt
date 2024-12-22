package com.sample.videoplayer.foundation.navigation.navigationmanagers

import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.appstate.navigate
import com.sample.videoplayer.foundation.appstate.popUp
import com.sample.videoplayer.foundation.navigation.VideoPlayerScreens

class HomeNavigationManager(private val appState: VideoPlayerAppState) {
    val backToPreviousScreen: () -> Unit = {
        appState.popUp()
    }

    val loadPlayerScreen: (String, String) -> Unit = { url, title ->
        appState.navigate(VideoPlayerScreens.VidePlayerScreen(url, title))
    }

    val loadDownloadsScreen: () -> Unit = {
        appState.navigate(VideoPlayerScreens.Downloads)
    }
}
