package com.sample.videoplayer.foundation.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.videoplayer.foundation.navigation.navigationmanagers.HomeNavigationManager
import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.navigation.VideoPlayerScreens
import com.sample.videoplayer.presentation.player.screen.VideoPlayerScreen

fun NavGraphBuilder.homeNavGraph(
    appState: VideoPlayerAppState
) {
    val homeNavigationManager = HomeNavigationManager(appState)
    // HomeScreen
    homeScreenComposable(homeNavigationManager)
    // Video Player screen
    videoPlayerScreen(homeNavigationManager)
}

private fun NavGraphBuilder.homeScreenComposable(
    homeNavigationManager: HomeNavigationManager
) {
    composable<VideoPlayerScreens.HomeScreen> {
        /*ProductsListingScreen(
            loadProductDetailScreen = homeNavigationManager.gotoProductsDetailScreen,
            gotoCartScreen = homeNavigationManager.gotoCartScreen
        )*/
    }
}

private fun NavGraphBuilder.videoPlayerScreen(
    homeNavigationManager: HomeNavigationManager
) {
    composable<VideoPlayerScreens.VidePlayerScreen> {

        VideoPlayerScreen(backToPreviousScreen = homeNavigationManager.backToPreviousScreen)
    }
}
