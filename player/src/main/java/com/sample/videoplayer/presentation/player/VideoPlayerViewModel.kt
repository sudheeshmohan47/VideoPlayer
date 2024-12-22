package com.sample.videoplayer.presentation.player

import androidx.lifecycle.viewModelScope
import com.sample.videoplayer.commonmodule.domain.model.Message
import com.sample.videoplayer.commonmodule.foundation.base.BaseViewModel
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.domain.VideoPlayerUseCase
import com.sample.videoplayer.domain.model.DownloadStatus
import com.sample.videoplayer.player.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = VideoPlayerViewModel.VideoPlayerViewModelFactory::class)
class VideoPlayerViewModel @AssistedInject constructor(
    private val videoPlayerUseCase: VideoPlayerUseCase,
    @Assisted("videoUrl") private val videoUrl: String,
    @Assisted("videoTitle") private val videoTitle: String,
    @Assisted initialScreenState: UiState<VideoPlayerUiModel>
) : BaseViewModel<UiState<VideoPlayerUiModel>, VideoPlayerAction, VideoPlayerEvent>(
    initialScreenState
) {

    @AssistedFactory
    interface VideoPlayerViewModelFactory {
        fun create(
            initialScreenState: UiState<VideoPlayerUiModel> = UiState.Loading(
                VideoPlayerUiModel()
            ),
            @Assisted("videoUrl") videoUrl: String,
            @Assisted("videoTitle") videoTitle: String,
        ): VideoPlayerViewModel
    }

    /**
     * This function is for handling various UI data changes in Compose
     */
    override fun reduce(
        currentState: UiState<VideoPlayerUiModel>,
        action: VideoPlayerAction
    ): UiState<VideoPlayerUiModel> {
        var returnState = currentState
        when (action) {

            is VideoPlayerAction.OnClickPlay -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        isPlaying = true
                    )
                )
            }

            is VideoPlayerAction.OnProgressChange -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        currentTime = action.progress.toLong()
                    )
                )
            }

            is VideoPlayerAction.SetBufferedPercentage -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        bufferedPercentage = action.bufferedPercentage
                    )
                )
            }

            is VideoPlayerAction.SetCurrentTime -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        currentTime = action.currentTime
                    )
                )
            }

            is VideoPlayerAction.SetIsPlaying -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        isPlaying = action.isPlaying
                    )
                )
            }

            is VideoPlayerAction.SetPlaybackState -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        playbackState = action.playbackState
                    )
                )
            }

            is VideoPlayerAction.SetShouldShowControls -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        shouldShowControls = action.shouldShowControls
                    )
                )
            }

            is VideoPlayerAction.SetTotalDuration -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        totalDuration = action.totalDuration
                    )
                )
            }

            is VideoPlayerAction.ToggleFullScreen -> {
                returnState = UiState.Result(
                    currentState.data?.copy(
                        isFullScreen = action.isFullScreen
                    )
                )
            }

            else -> Unit
        }
        return returnState
    }

    /**
     * This function is used for handling events like navigating to the next screen,
     * fetching results from the backend, or showing something in the UI.
     */
    override fun runSideEffect(
        action: VideoPlayerAction,
        currentState: UiState<VideoPlayerUiModel>
    ) {
        when (action) {
            is VideoPlayerAction.OnClickDownload -> {
                downloadMedia(currentState)
            }

            is VideoPlayerAction.OnClickBackNavigation -> {
                sendEvent(VideoPlayerEvent.BackToPrevScreen)
            }

            is VideoPlayerAction.RefreshData -> {
                loadMediaStatus()
            }

            else -> Unit
        }
    }

    private fun loadMediaStatus() {
        viewModelScope.launch(Dispatchers.IO) {

            val downloadStatus = videoPlayerUseCase.getMediaDownloadStatus(videoUrl)
            val playbackProgress = videoPlayerUseCase.getPlaybackProgress(videoUrl)

            sendState(
                UiState.Result(
                    uiState.value.data?.copy(
                        downloadStatus = downloadStatus,
                        currentTime = playbackProgress
                    )
                )
            )
        }
    }

    private fun downloadMedia(currentState: UiState<VideoPlayerUiModel>) {
        val downloadStatus = currentState.data?.downloadStatus
        if (downloadStatus == DownloadStatus.DOWNLOADING) {
            sendEvent(VideoPlayerEvent.ShowMessage(Message(R.string.message_download_in_progress)))
        } else if (downloadStatus == DownloadStatus.NOT_DOWNLOADED) {
            sendState(
                UiState.Result(
                    currentState.data?.copy(
                        downloadStatus = DownloadStatus.DOWNLOADING
                    )
                )
            )
            viewModelScope.launch(Dispatchers.IO) {
                videoPlayerUseCase.updateDownloadStatus(
                    url = videoUrl,
                    title = videoTitle,
                    status = DownloadStatus.DOWNLOADING
                )
            }
        }
    }
}
