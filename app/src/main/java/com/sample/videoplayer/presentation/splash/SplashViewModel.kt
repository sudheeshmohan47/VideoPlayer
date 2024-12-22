package com.sample.videoplayer.presentation.splash

import androidx.lifecycle.viewModelScope
import com.sample.videoplayer.commonmodule.foundation.base.BaseViewModel
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val splashViewModelCreationCallback = { factory: SplashViewModel.SplashViewModelFactory ->
    factory.create()
}

@HiltViewModel(assistedFactory = SplashViewModel.SplashViewModelFactory::class)
class SplashViewModel @AssistedInject constructor(
    @Assisted initialScreenState: UiState<SplashUiModel>
) : BaseViewModel<UiState<SplashUiModel>, SplashAction, SplashEvent>(initialScreenState) {

    @AssistedFactory
    interface SplashViewModelFactory {
        fun create(initialScreenState: UiState<SplashUiModel> = UiState.Loading(SplashUiModel())): SplashViewModel
    }

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            // Here we can do initial loading of data like checking login status
            // App intro shown status etc...
            delay(3000)
            sendAction(SplashAction.UpdateInitialDataStatus)
        }
    }

    /**
     * This function is for handling various ui data changes in compose
     */
    override fun reduce(
        currentState: UiState<SplashUiModel>,
        action: SplashAction
    ): UiState<SplashUiModel> {
        var returnState = currentState
        when (action) {
            is SplashAction.UpdateInitialDataStatus -> {
                    returnState =
                        UiState.Result(
                            currentState.data?.copy(
                                initialDataLoaded = true
                            )
                        )
                }
            }
        return returnState
    }

    /**
     * This function is used for handling events like navigate to next screen,
     * get results from backend and show something wtc
     */
    override fun runSideEffect(action: SplashAction, currentState: UiState<SplashUiModel>) {
        // TODO Handle side effects
    }
}
