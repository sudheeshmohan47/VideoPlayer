package com.sample.videoplayer.presentation.home

import com.sample.videoplayer.commonmodule.domain.model.Message
import com.sample.videoplayer.commonmodule.foundation.base.Action
import com.sample.videoplayer.commonmodule.foundation.base.Event
import com.sample.videoplayer.domain.model.MediaFile

data class HomeUiModel(
    val mediaList: List<MediaFile> = emptyList()
)

sealed class HomeAction : Action {
    data object RefreshData : HomeAction()
    data class OnClickMediaFile(val mediaFile: MediaFile) : HomeAction()
    data object OnClickDownloads: HomeAction()
}

sealed class HomeEvent : Event {
    data object GotoDownloadsScreen : HomeEvent()
    data class GotoPlayerScreen(val title: String, val videoUrl: String) : HomeEvent()
    data class ShowMessage(val message: Message) : HomeEvent()
}
