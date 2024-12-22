package com.sample.videoplayer.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.sample.designsystem.videoplayer.components.AlertDialogType
import com.sample.designsystem.videoplayer.components.VideoPlayerCustomDialog
import com.sample.designsystem.videoplayer.foundation.ui.VideoPlayerTheme
import com.sample.videoplayer.R
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.commonmodule.foundation.navigation.customcomponents.popBackStackWithLifecycle
import com.sample.videoplayer.foundation.appstate.VideoPlayerApp
import com.sample.videoplayer.foundation.appstate.VideoPlayerAppState
import com.sample.videoplayer.foundation.appstate.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var appState: VideoPlayerAppState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    appState = rememberAppState()
                    navController = appState.navController
                    val mainActivityViewModel: MainActivityViewModel = hiltViewModel(
                        creationCallback = mainActivityViewModelCreationCallback
                    )

                    val uiState by mainActivityViewModel.uiState.collectAsStateWithLifecycle()

                    VideoPlayerApp(
                        appState = appState
                    )

                    // For handling back press
                    HandleBackPress(uiState) {
                        mainActivityViewModel.sendAction(it)
                    }
                }
            }
        }
    }

    @Composable
    private fun HandleBackPress(
        uiState: UiState<MainActivityUiModel>,
        onAction: (MainActivityAction) -> Unit
    ) {
        BackHandler(
            onBack = {
                if (navController.previousBackStackEntry?.id == null && !isFinishing) {
                    onAction(
                        MainActivityAction.ShowAppExitDialog(
                            true
                        )
                    )
                } else {
                    navController.popBackStackWithLifecycle()
                }
            }
        )
        if (uiState.data?.showAppExitDialog == true) {
            ShowAppExitDialog {
                onAction(it)
            }
        }
    }

    @Composable
    private fun ShowAppExitDialog(onAction: (MainActivityAction) -> Unit) {
        VideoPlayerCustomDialog(
            dialogType = AlertDialogType.SHEET,
            title = stringResource(R.string.exit_confirmation_title),
            description = stringResource(R.string.exit_confirmation_message),
            positiveButtonLabel = stringResource(id = R.string.dialog_ok),
            negativeButtonLabel = stringResource(id = R.string.dialog_cancel),
            onPositiveButtonClick = {
                onAction(MainActivityAction.ShowAppExitDialog(false))
                finish()
            },
            onDismiss = {
                onAction(MainActivityAction.ShowAppExitDialog(false))
            }
        )
    }
}
