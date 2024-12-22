package com.sample.videoplayer.foundation.appstate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sample.videoplayer.foundation.navigation.VideoPlayerScreens
import com.sample.videoplayer.foundation.navigation.navgraph.rootGraph

@Composable
fun VideoPlayerApp(
    appState: VideoPlayerAppState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = appState.navController,
            startDestination = VideoPlayerScreens.Splash,
        ) {
            rootGraph(appState)
        }
    }
}
