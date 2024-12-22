package com.sample.videoplayer.foundation.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.navigation.VideoPlayerScreens
import com.sample.videoplayer.foundation.navigation.navigationmanagers.HomeNavigationManager
import com.sample.videoplayer.presentation.downloads.screen.DownloadsScreen
import com.sample.videoplayer.presentation.home.screen.HomeScreen
import com.sample.videoplayer.presentation.player.screen.VideoPlayerScreen

fun NavGraphBuilder.homeNavGraph(
    appState: VideoPlayerAppState
) {
    val homeNavigationManager = HomeNavigationManager(appState)
    // HomeScreen
    homeScreenComposable(homeNavigationManager)
    // Video Player screen
    videoPlayerScreen(homeNavigationManager)
    // Downloads screen
    downloadsScreen(homeNavigationManager)
}

private fun NavGraphBuilder.homeScreenComposable(
    homeNavigationManager: HomeNavigationManager
) {
    composable<VideoPlayerScreens.HomeScreen> {
        HomeScreen(
            loadPlayerScreen = homeNavigationManager.loadPlayerScreen,
            loadDownloadsScreen = homeNavigationManager.loadDownloadsScreen
        )
    }
}

private fun NavGraphBuilder.videoPlayerScreen(
    homeNavigationManager: HomeNavigationManager
) {
    composable<VideoPlayerScreens.VidePlayerScreen> {
        val playerScreen = it.toRoute<VideoPlayerScreens.VidePlayerScreen>()
        VideoPlayerScreen(
            backToPreviousScreen = homeNavigationManager.backToPreviousScreen,
            mediaTitle = playerScreen.title,
            mediaUrl = playerScreen.url
        )
    }
}

private fun NavGraphBuilder.downloadsScreen(
    homeNavigationManager: HomeNavigationManager
) {
    composable<VideoPlayerScreens.Downloads> {
        DownloadsScreen(
            backToPrevScreen = homeNavigationManager.backToPreviousScreen,
            loadPlayerScreen = homeNavigationManager.loadPlayerScreen
        )
    }
}
