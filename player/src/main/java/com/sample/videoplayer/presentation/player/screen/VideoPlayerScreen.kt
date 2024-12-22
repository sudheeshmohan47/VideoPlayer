package com.sample.videoplayer.presentation.player.screen

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.sample.designsystem.videoplayer.components.VideoPlayerSnackBarHost
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.commonmodule.utils.ONE_SECOND
import com.sample.videoplayer.commonmodule.utils.handleErrorMessage
import com.sample.videoplayer.presentation.player.ExoPlayerAction
import com.sample.videoplayer.presentation.player.VideoPlayerAction
import com.sample.videoplayer.presentation.player.VideoPlayerEvent
import com.sample.videoplayer.presentation.player.VideoPlayerUiModel
import com.sample.videoplayer.presentation.player.VideoPlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    mediaTitle: String,
    mediaUrl: String,
    backToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier,
    videoPlayerViewModel: VideoPlayerViewModel = hiltViewModel<VideoPlayerViewModel,
        VideoPlayerViewModel.VideoPlayerViewModelFactory> { factory ->
        factory.create(videoUrl = mediaUrl, videoTitle = mediaTitle)
    }
) {

    val context = LocalContext.current
    val topAppBarState = rememberTopAppBarState()
    val coroutineScope = rememberCoroutineScope()
    val videoPlayerUiState: UiState<VideoPlayerUiModel> by videoPlayerViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val exoPlayer = remember {
        createExoPlayer(context, mediaUrl, mediaTitle)
    }

    LifecycleResumeEffect(Unit) {
        videoPlayerViewModel.sendAction(VideoPlayerAction.RefreshData)
        onPauseOrDispose { }
    }

    Box(modifier = modifier.fillMaxSize()) {
        VideoPlayerScreenMainContent(
            topAppBarState = topAppBarState,
            context = context,
            videoPlayerUiState = videoPlayerUiState,
            addListenerToExoPlayer = { listener: Player.Listener ->
                exoPlayer.addListener(listener)
            },
            removeListenerFromExoPlayer = { listener: Player.Listener ->
                exoPlayer.removeListener(listener)
            },
            releaseExoPlayer = {
                exoPlayer.release()
            },
            onAction = { action: VideoPlayerAction ->
                videoPlayerViewModel.sendAction(action)
            },
            exoPlayer = exoPlayer,
            onExoPlayerAction = { action: ExoPlayerAction ->
                when (action) {
                    ExoPlayerAction.Pause -> exoPlayer.pause()
                    ExoPlayerAction.Play -> exoPlayer.play()
                    ExoPlayerAction.SeekBack -> exoPlayer.seekBack()
                    ExoPlayerAction.SeekForward -> exoPlayer.seekForward()
                    is ExoPlayerAction.SeekTo -> exoPlayer.seekTo(action.timeMs)
                    ExoPlayerAction.SetPlayWhenReady -> exoPlayer.playWhenReady = true
                }
            }
        )
        VideoPlayerSnackBarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        HandleUIStateChanges(
            videoPlayerScreenEvent = videoPlayerViewModel.uiEvent,
            snackBarHostState = snackBarHostState,
            backToPreviousScreen = backToPreviousScreen,
            coroutineScope = coroutineScope
        )
    }
}

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreenMainContent(
    exoPlayer: ExoPlayer,
    videoPlayerUiState: UiState<VideoPlayerUiModel>,
    addListenerToExoPlayer: (Player.Listener) -> Unit,
    removeListenerFromExoPlayer: (Player.Listener) -> Unit,
    releaseExoPlayer: () -> Unit,
    onExoPlayerAction: (ExoPlayerAction) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    onAction: (VideoPlayerAction) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        VideoPlayerContent(
            exoPlayer = exoPlayer,
            videoPlayerUiState = videoPlayerUiState,
            addListenerToExoPlayer = addListenerToExoPlayer,
            removeListenerFromExoPlayer = removeListenerFromExoPlayer,
            releaseExoPlayer = releaseExoPlayer,
            onExoPlayerAction = onExoPlayerAction,
            onAction = onAction,
            context = context
        )
        VideoPlayerTopAppBarSection(
            topAppBarState = topAppBarState,
            videoPlayerUiState = videoPlayerUiState,
            onAction = onAction,
            title = exoPlayer.mediaMetadata.displayTitle.toString()
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerContent(
    exoPlayer: ExoPlayer,
    videoPlayerUiState: UiState<VideoPlayerUiModel>,
    addListenerToExoPlayer: (Player.Listener) -> Unit,
    removeListenerFromExoPlayer: (Player.Listener) -> Unit,
    releaseExoPlayer: () -> Unit,
    onExoPlayerAction: (ExoPlayerAction) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    onAction: (VideoPlayerAction) -> Unit
) {
    val shouldShowControls = videoPlayerUiState.data?.shouldShowControls ?: false
    val onActionState by rememberUpdatedState(onAction)
    val addListenerToExoPlayerState by rememberUpdatedState(addListenerToExoPlayer)
    val removeListenerFromExoPlayerState by rememberUpdatedState(removeListenerFromExoPlayer)
    val releaseExoPlayerState by rememberUpdatedState(releaseExoPlayer)

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onAction(VideoPlayerAction.SetShouldShowControls(shouldShowControls.not()))
            }
    ) {
        LaunchedEffect(Unit) {
            while (true) {
                onActionState(
                    VideoPlayerAction.SetCurrentTime(exoPlayer.currentPosition.coerceAtLeast(0L))
                )
                delay(ONE_SECOND) // Update every second
            }
        }
        DisposableEffect(key1 = Unit) {
            val listener: Player.Listener =
                object : Player.Listener {
                    override fun onEvents(
                        player: Player,
                        events: Player.Events
                    ) {
                        super.onEvents(player, events)
                        onActionState(
                            VideoPlayerAction.SetTotalDuration(
                                player.duration.coerceAtLeast(
                                    0L
                                )
                            )
                        )
                        onActionState(
                            VideoPlayerAction.SetCurrentTime(
                                player.currentPosition.coerceAtLeast(
                                    0L
                                )
                            )
                        )
                        onActionState(VideoPlayerAction.SetBufferedPercentage(player.bufferedPercentage))
                        onActionState(VideoPlayerAction.SetIsPlaying(player.isPlaying))
                        onActionState(VideoPlayerAction.SetPlaybackState(player.playbackState))
                    }
                }
            addListenerToExoPlayerState(listener)
            onDispose {
                removeListenerFromExoPlayerState(listener)
                releaseExoPlayerState()
            }
        }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // Set resize mode to fill the available space
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    // Hide unnecessary player controls
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    setShowFastForwardButton(false)
                    setShowRewindButton(false)
                    this.useController = false
                }
            }
        )

        PlayerControlsSection(
            videoPlayerUiState = videoPlayerUiState,
            isExoplayerPlaying = videoPlayerUiState.data?.isPlaying ?: false,
            onExoPlayerAction = onExoPlayerAction,
            onAction = onAction,
            context = context
        )
    }
}

@Composable
private fun HandleUIStateChanges(
    videoPlayerScreenEvent: Flow<VideoPlayerEvent>,
    snackBarHostState: SnackbarHostState,
    backToPreviousScreen: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val context = LocalContext.current
    val backToPreviousScreenState by rememberUpdatedState(backToPreviousScreen)

    LaunchedEffect(Unit) {
        videoPlayerScreenEvent.collectLatest { event ->
            when (event) {
                is VideoPlayerEvent.BackToPrevScreen -> {
                    backToPreviousScreenState()
                }

                is VideoPlayerEvent.ShowMessage -> {
                    handleErrorMessage(
                        context = context,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        errorMessage = event.message
                    )
                }
            }
        }
    }
}
