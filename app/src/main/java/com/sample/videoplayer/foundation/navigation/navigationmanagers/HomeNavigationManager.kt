package com.sample.videoplayer.foundation.navigation.navigationmanagers

import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.appstate.popUp

class HomeNavigationManager(private val appState: VideoPlayerAppState) {
    val backToPreviousScreen: () -> Unit = {
        appState.popUp()
    }
}
