package com.sample.videoplayer.foundation.navigation.navigationmanagers

import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.appstate.navigateWithPopBackstack
import com.sample.videoplayer.foundation.navigation.VideoPlayerScreens

class StartupNavigationManager(private val appState: VideoPlayerAppState) {

    val goToHomeScreen: () -> Unit = {
        appState.navigateWithPopBackstack(VideoPlayerScreens.VidePlayerScreen)
    }
}
