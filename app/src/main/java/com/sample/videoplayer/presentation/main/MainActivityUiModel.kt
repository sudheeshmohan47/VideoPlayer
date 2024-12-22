package com.sample.videoplayer.presentation.main

import com.sample.videoplayer.commonmodule.foundation.base.Action
import com.sample.videoplayer.commonmodule.foundation.base.Event

data class MainActivityUiModel(
    val showAppExitDialog: Boolean = false
)

sealed class MainActivityAction : Action {
    data class ShowAppExitDialog(val showAppExitDialog: Boolean) : MainActivityAction()
}

sealed class MainActivityEvent : Event
