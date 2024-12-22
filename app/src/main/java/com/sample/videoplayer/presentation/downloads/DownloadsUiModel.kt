package com.sample.videoplayer.presentation.downloads

import com.sample.videoplayer.commonmodule.domain.model.Message
import com.sample.videoplayer.commonmodule.foundation.base.Action
import com.sample.videoplayer.commonmodule.foundation.base.Event
import com.sample.videoplayer.domain.model.MediaFile

data class DownloadsUiModel(
    val mediaList: List<MediaFile> = emptyList()
)

sealed class DownloadsAction : Action {
    data object RefreshData : DownloadsAction()
    data class OnClickMediaFile(val mediaFile: MediaFile) : DownloadsAction()
    data object OnClickBackNavigation : DownloadsAction()
}

sealed class DownloadsEvent : Event {
    data class GotoPlayerScreen(val title: String, val videoUrl: String) : DownloadsEvent()
    data object BackToPrevScreen : DownloadsEvent()
    data class ShowMessage(val message: Message) : DownloadsEvent()
}
