package com.sample.videoplayer.presentation.splash

import com.sample.videoplayer.commonmodule.foundation.base.Action
import com.sample.videoplayer.commonmodule.foundation.base.Event

data class SplashUiModel(
    val initialDataLoaded: Boolean = false
)

sealed class SplashAction : Action {
    data object UpdateInitialDataStatus : SplashAction()
}

sealed class SplashEvent : Event
