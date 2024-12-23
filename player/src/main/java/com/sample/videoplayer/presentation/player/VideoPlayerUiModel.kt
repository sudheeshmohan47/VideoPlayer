package com.sample.videoplayer.presentation.player

import com.sample.videoplayer.commonmodule.domain.model.Message
import com.sample.videoplayer.commonmodule.foundation.base.Action
import com.sample.videoplayer.commonmodule.foundation.base.Event
import com.sample.videoplayer.datastoragemodule.data.database.domain.model.DownloadStatus

data class VideoPlayerUiModel(
    val downloadStatus: DownloadStatus = DownloadStatus.NOT_DOWNLOADED,
    val shouldShowControls: Boolean = false,
    val isPlaying: Boolean = false,
    val totalDuration: Long = 0L,
    val currentTime: Long = 0L,
    val bufferedPercentage: Int = 0,
    val playbackState: Int = 0,
    val isFullScreen: Boolean = false
)

sealed class VideoPlayerAction : Action {
    data object OnClickBackNavigation : VideoPlayerAction()
    data object RefreshData : VideoPlayerAction()
    data object OnClickDownload : VideoPlayerAction()
    data object OnClickPlay : VideoPlayerAction()
    data class SetIsPlaying(val isPlaying: Boolean) : VideoPlayerAction()
    data class OnProgressChange(val progress: Int) : VideoPlayerAction()
    data class SetShouldShowControls(val shouldShowControls: Boolean) : VideoPlayerAction()
    data class SetTotalDuration(val totalDuration: Long) : VideoPlayerAction()
    data class SetCurrentTime(val currentTime: Long) : VideoPlayerAction()
    data class SetBufferedPercentage(val bufferedPercentage: Int) : VideoPlayerAction()
    data class SetPlaybackState(val playbackState: Int) : VideoPlayerAction()
    data class ToggleFullScreen(val isFullScreen: Boolean) : VideoPlayerAction()
    data object SaveCurrentProgressToDb : VideoPlayerAction()
}

sealed class VideoPlayerEvent : Event {
    data object BackToPrevScreen : VideoPlayerEvent()
    data class ShowMessage(val message: Message) : VideoPlayerEvent()
    data class SeekExoPlayer(val timeMs: Long) : VideoPlayerEvent()
}

sealed class ExoPlayerAction : Action {
    data object SeekBack : ExoPlayerAction()
    data object SeekForward : ExoPlayerAction()
    data object Pause : ExoPlayerAction()
    data object SetPlayWhenReady : ExoPlayerAction()
    data object Play : ExoPlayerAction()
    data class SeekTo(val timeMs: Long) : ExoPlayerAction()
}
