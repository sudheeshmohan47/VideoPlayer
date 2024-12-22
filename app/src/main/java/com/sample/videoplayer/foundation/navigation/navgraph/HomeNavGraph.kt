package com.sample.videoplayer.foundation.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.videoplayer.foundation.navigation.navigationmanagers.HomeNavigationManager
import com.sample.onlinestore.presentation.dashboard.BottomNavItem
import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState

fun NavGraphBuilder.homeNavGraph(
    appState: VideoPlayerAppState
) {
    val homeNavigationManager = HomeNavigationManager(appState)
    // HomeScreen
    homeScreenComposable(homeNavigationManager)
}

private fun NavGraphBuilder.homeScreenComposable(
    homeNavigationManager: HomeNavigationManager
) {
    composable<BottomNavItem.Home> {
        /*ProductsListingScreen(
            loadProductDetailScreen = homeNavigationManager.gotoProductsDetailScreen,
            gotoCartScreen = homeNavigationManager.gotoCartScreen
        )*/
    }
}
