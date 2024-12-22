package com.sample.videoplayer.presentation.downloads

import androidx.lifecycle.viewModelScope
import com.sample.videoplayer.commonmodule.domain.exception.DomainException
import com.sample.videoplayer.commonmodule.domain.exception.mapErrorMessage
import com.sample.videoplayer.commonmodule.domain.model.Message
import com.sample.videoplayer.commonmodule.foundation.base.BaseViewModel
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.domain.downloads.DownloadsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val downloadsViewModelCreationCallback =
    { factory: DownloadsViewModel.DownloadsViewModelFactory ->
        factory.create()
    }

@HiltViewModel(assistedFactory = DownloadsViewModel.DownloadsViewModelFactory::class)
class DownloadsViewModel @AssistedInject constructor(
    private val downloadsUseCase: DownloadsUseCase,
    @Assisted initialScreenState: UiState<DownloadsUiModel>
) : BaseViewModel<UiState<DownloadsUiModel>, DownloadsAction, DownloadsEvent>(
    initialScreenState
) {

    @AssistedFactory
    interface DownloadsViewModelFactory {
        fun create(
            initialScreenState: UiState<DownloadsUiModel> = UiState.Result(
                DownloadsUiModel()
            )
        ): DownloadsViewModel
    }

    override fun reduce(
        currentState: UiState<DownloadsUiModel>,
        action: DownloadsAction
    ): UiState<DownloadsUiModel> {
        return handleReducerAction(currentState = currentState, action = action)
    }

    private fun handleReducerAction(
        action: DownloadsAction,
        currentState: UiState<DownloadsUiModel>
    ): UiState<DownloadsUiModel> {

        var returnState = currentState
        when (action) {

            // Handle instant ui change actions here
            else -> Unit
        }
        return returnState
    }

    override fun runSideEffect(
        action: DownloadsAction,
        currentState: UiState<DownloadsUiModel>
    ) {
        when (action) {

            is DownloadsAction.RefreshData -> {
                loadDownloadedMediaFiles()
            }

            is DownloadsAction.OnClickBackNavigation -> {
                sendEvent(DownloadsEvent.BackToPrevScreen)
            }

            is DownloadsAction.OnClickMediaFile -> {
                sendEvent(
                    DownloadsEvent.GotoPlayerScreen(
                        title = action.mediaFile.title,
                        videoUrl = action.mediaFile.videoUrl
                    )
                )
            }
        }
    }

    private fun loadDownloadedMediaFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mediaFiles = downloadsUseCase.loadDownloadedMediaFiles()
                // Update the UI state with the new list of media
                sendState(
                    UiState.Result(
                        uiState.value.data?.copy(
                            mediaList = mediaFiles.data ?: emptyList()
                        )
                    )
                )
            } catch (exception: DomainException) {
                handleException(exception, uiState.value)
            }
        }
    }

    /**
     * Handles exceptions that occur during loading media files.*
     * @param exception The exception to handle.
     * @param currentState The current UI state to maintain while handling the exception.
     */
    private fun handleException(
        exception: DomainException,
        currentState: UiState<DownloadsUiModel>
    ) {
        val errorMessage = mapErrorMessage(exception)
        sendState(
            UiState.Result(
                currentState.data
            )
        )
        sendEvent(
            DownloadsEvent.ShowMessage(
                Message(
                    errorMessage.messageResId,
                    errorMessage.message
                )
            )
        )
    }
}
