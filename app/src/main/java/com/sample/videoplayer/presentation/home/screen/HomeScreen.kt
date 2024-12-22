package com.sample.videoplayer.presentation.home.screen

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
import com.sample.videoplayer.presentation.home.HomeAction
import com.sample.videoplayer.presentation.home.HomeEvent
import com.sample.videoplayer.presentation.home.HomeUiModel
import com.sample.videoplayer.presentation.home.HomeViewModel
import com.sample.videoplayer.presentation.home.homeViewModelCreationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    loadPlayerScreen: (String, String) -> Unit,
    loadDownloadsScreen: () -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(
        creationCallback = homeViewModelCreationCallback
    )
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val topAppBarState = rememberTopAppBarState()
    val homeListState: LazyListState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LifecycleResumeEffect(Unit) {
        homeViewModel.sendAction(HomeAction.RefreshData)
        onPauseOrDispose { }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HomeScreenMainContent(
            homeUiState = homeUiState,
            onAction = {
                homeViewModel.sendAction(it)
            },
            topAppBarState = topAppBarState,
            homeListState = homeListState,
            screenWidth = screenWidth
        )
        VideoPlayerSnackBarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        HandleUIStateChanges(
            homeScreenUiState = homeUiState,
            snackBarHostState = snackBarHostState,
            homeScreenEvent = homeViewModel.uiEvent,
            loadPlayerScreen = loadPlayerScreen,
            loadDownloadsScreen = loadDownloadsScreen,
            coroutineScope = rememberCoroutineScope()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMainContent(
    homeUiState: UiState<HomeUiModel>,
    onAction: (HomeAction) -> Unit,
    screenWidth: Dp,
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    homeListState: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HomeScreenTopAppBarSection(
            topAppBarState = topAppBarState,
            onAction = onAction
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        HomeScreenContent(
            homeUiState = homeUiState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = VideoPlayerSpacing.SMALL.dp()),
            onAction = onAction,
            homeListState = homeListState,
            screenWidth = screenWidth
        )
    }
}

@Composable
private fun HandleUIStateChanges(
    homeScreenUiState: UiState<HomeUiModel>,
    homeScreenEvent: Flow<HomeEvent>,
    snackBarHostState: SnackbarHostState,
    loadPlayerScreen: (String, String) -> Unit,
    loadDownloadsScreen: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val context = LocalContext.current
    val loadPlayerScreenState by rememberUpdatedState(loadPlayerScreen)
    val loadDownloadsScreenState by rememberUpdatedState(loadDownloadsScreen)

    LaunchedEffect(Unit) {
        homeScreenEvent.collectLatest { event ->
            when (event) {
                is HomeEvent.GotoPlayerScreen -> {
                    loadPlayerScreenState(event.videoUrl, event.title)
                }

                is HomeEvent.ShowMessage -> {
                    handleErrorMessage(
                        context = context,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        errorMessage = event.message
                    )
                }

                is HomeEvent.GotoDownloadsScreen -> {
                    loadDownloadsScreenState()
                }
            }
        }
    }

    if (homeScreenUiState is UiState.Loading) {
        ShowDashedProgressIndicator()
    }
}
