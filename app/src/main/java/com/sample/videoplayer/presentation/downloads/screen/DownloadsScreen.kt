package com.sample.videoplayer.presentation.downloads.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.designsystem.videoplayer.components.ShowDashedProgressIndicator
import com.sample.designsystem.videoplayer.components.VideoPlayerSnackBarHost
import com.sample.designsystem.videoplayer.foundation.VideoPlayerSpacing
import com.sample.designsystem.videoplayer.foundation.dp
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.commonmodule.utils.handleErrorMessage
import com.sample.videoplayer.presentation.downloads.DownloadsAction
import com.sample.videoplayer.presentation.downloads.DownloadsEvent
import com.sample.videoplayer.presentation.downloads.DownloadsUiModel
import com.sample.videoplayer.presentation.downloads.DownloadsViewModel
import com.sample.videoplayer.presentation.downloads.downloadsViewModelCreationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    loadPlayerScreen: (String, String) -> Unit,
    backToPrevScreen: () -> Unit,
    modifier: Modifier = Modifier,
    downloadsViewModel: DownloadsViewModel = hiltViewModel(
        creationCallback = downloadsViewModelCreationCallback
    )
) {
    val downloadsUiState by downloadsViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val topAppBarState = rememberTopAppBarState()
    val downloadsListState: LazyListState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LifecycleResumeEffect(Unit) {
        downloadsViewModel.sendAction(DownloadsAction.RefreshData)
        onPauseOrDispose { }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        DownloadsScreenMainContent(
            downloadsUiState = downloadsUiState,
            onAction = {
                downloadsViewModel.sendAction(it)
            },
            topAppBarState = topAppBarState,
            downloadsListState = downloadsListState,
            screenWidth = screenWidth
        )
        VideoPlayerSnackBarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        HandleUIStateChanges(
            downloadsScreenUiState = downloadsUiState,
            snackBarHostState = snackBarHostState,
            downloadsScreenEvent = downloadsViewModel.uiEvent,
            loadPlayerScreen = loadPlayerScreen,
            backToPrevScreen = backToPrevScreen,
            coroutineScope = rememberCoroutineScope()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreenMainContent(
    downloadsUiState: UiState<DownloadsUiModel>,
    onAction: (DownloadsAction) -> Unit,
    screenWidth: Dp,
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    downloadsListState: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        DownloadsScreenTopAppBarSection(
            topAppBarState = topAppBarState,
            onAction = onAction
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        DownloadsScreenContent(
            downloadsUiState = downloadsUiState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = VideoPlayerSpacing.SMALL.dp()),
            onAction = onAction,
            downloadsListState = downloadsListState,
            screenWidth = screenWidth
        )
    }
}

@Composable
private fun HandleUIStateChanges(
    downloadsScreenUiState: UiState<DownloadsUiModel>,
    downloadsScreenEvent: Flow<DownloadsEvent>,
    snackBarHostState: SnackbarHostState,
    loadPlayerScreen: (String, String) -> Unit,
    backToPrevScreen: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val context = LocalContext.current
    val loadPlayerScreenState by rememberUpdatedState(loadPlayerScreen)
    val backToPrevScreenState by rememberUpdatedState(backToPrevScreen)


    LaunchedEffect(Unit) {
        downloadsScreenEvent.collectLatest { event ->
            when (event) {
                is DownloadsEvent.GotoPlayerScreen -> {
                    loadPlayerScreenState(event.videoUrl, event.title)
                }

                is DownloadsEvent.ShowMessage -> {
                    handleErrorMessage(
                        context = context,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        errorMessage = event.message
                    )
                }

                DownloadsEvent.BackToPrevScreen -> {
                    backToPrevScreenState()
                }
            }
        }
    }

    if (downloadsScreenUiState is UiState.Loading) {
        ShowDashedProgressIndicator()
    }
}
