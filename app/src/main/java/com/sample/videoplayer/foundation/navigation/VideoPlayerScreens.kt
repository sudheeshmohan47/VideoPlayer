package com.sample.videoplayer.foundation.navigation

import com.sample.videoplayer.commonmodule.foundation.base.BaseScreen
import kotlinx.serialization.Serializable

// Sealed class for startup screens
@Serializable
sealed class VideoPlayerScreens : BaseScreen() {

    @Serializable
    data object Splash : VideoPlayerScreens()

    @Serializable
    data object HomeScreen : VideoPlayerScreens()

    @Serializable
    data object Downloads : VideoPlayerScreens()

    @Serializable
    data object VidePlayerScreen : VideoPlayerScreens()
}
