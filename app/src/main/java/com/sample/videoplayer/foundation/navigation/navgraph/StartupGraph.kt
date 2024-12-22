package com.sample.videoplayer.foundation.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import com.sample.videoplayer.commonmodule.foundation.navigation.customcomponents.slideComposable
import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.navigation.VideoPlayerScreens
import com.sample.videoplayer.foundation.navigation.navigationmanagers.StartupNavigationManager
import com.sample.videoplayer.presentation.splash.screen.SplashScreen

fun NavGraphBuilder.startupGraph(
    appState: VideoPlayerAppState
) {
    val startupNavigationManager = StartupNavigationManager(appState)

    // Splash Screen
    splashScreenComposable(startupNavigationManager)
}

private fun NavGraphBuilder.splashScreenComposable(
    startupNavigationManager: StartupNavigationManager
) {
    slideComposable<VideoPlayerScreens.Splash> {
        SplashScreen(
            navigateToHome = startupNavigationManager.goToHomeScreen
        )
    }
}
