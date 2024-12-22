package com.sample.videoplayer.presentation.home

import androidx.lifecycle.viewModelScope
import com.sample.videoplayer.commonmodule.domain.exception.DomainException
import com.sample.videoplayer.commonmodule.domain.exception.mapErrorMessage
import com.sample.videoplayer.commonmodule.domain.model.Message
import com.sample.videoplayer.commonmodule.foundation.base.BaseViewModel
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.domain.home.HomeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val homeViewModelCreationCallback =
    { factory: HomeViewModel.HomeViewModelFactory ->
        factory.create()
    }

@HiltViewModel(assistedFactory = HomeViewModel.HomeViewModelFactory::class)
class HomeViewModel @AssistedInject constructor(
    private val homeUseCase: HomeUseCase,
    @Assisted initialScreenState: UiState<HomeUiModel>
) : BaseViewModel<UiState<HomeUiModel>, HomeAction, HomeEvent>(
    initialScreenState
) {

    @AssistedFactory
    interface HomeViewModelFactory {
        fun create(
            initialScreenState: UiState<HomeUiModel> = UiState.Result(
                HomeUiModel()
            )
        ): HomeViewModel
    }

    override fun reduce(
        currentState: UiState<HomeUiModel>,
        action: HomeAction
    ): UiState<HomeUiModel> {
        return handleReducerAction(currentState = currentState, action = action)
    }

    private fun handleReducerAction(
        action: HomeAction,
        currentState: UiState<HomeUiModel>
    ): UiState<HomeUiModel> {

        var returnState = currentState
        when (action) {

            // Handle instant ui change actions here
            else -> Unit
        }
        return returnState
    }

    override fun runSideEffect(
        action: HomeAction,
        currentState: UiState<HomeUiModel>
    ) {
        when (action) {

            is HomeAction.RefreshData -> {
                loadMediaFiles()
            }

            is HomeAction.OnClickDownloads -> {
                sendEvent(HomeEvent.GotoDownloadsScreen)
            }

            is HomeAction.OnClickMediaFile -> {
                sendEvent(
                    HomeEvent.GotoPlayerScreen(
                        title = action.mediaFile.title,
                        videoUrl = action.mediaFile.videoUrl
                    )
                )
            }
        }
    }

    private fun loadMediaFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mediaFiles = homeUseCase.loadMediaFiles()
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
        currentState: UiState<HomeUiModel>
    ) {
        val errorMessage = mapErrorMessage(exception)
        sendState(
            UiState.Result(
                currentState.data
            )
        )
        sendEvent(
            HomeEvent.ShowMessage(
                Message(
                    errorMessage.messageResId,
                    errorMessage.message
                )
            )
        )
    }
}
