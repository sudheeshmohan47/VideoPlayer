package com.sample.videoplayer.foundation.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState

fun NavGraphBuilder.rootGraph(appState: VideoPlayerAppState) {
    startupGraph(appState)
    homeNavGraph(appState)
}
