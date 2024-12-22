package com.sample.videoplayer.presentation.player.screen

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.sample.designsystem.videoplayer.foundation.VideoPlayerSize
import com.sample.designsystem.videoplayer.foundation.VideoPlayerSpacing
import com.sample.designsystem.videoplayer.foundation.dp
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.commonmodule.utils.formatMinSec
import com.sample.videoplayer.commonmodule.utils.toMinutesAndSeconds
import com.sample.videoplayer.player.R
import com.sample.videoplayer.presentation.player.ExoPlayerAction
import com.sample.videoplayer.presentation.player.VideoPlayerAction
import com.sample.videoplayer.presentation.player.VideoPlayerUiModel

@OptIn(UnstableApi::class)
fun createExoPlayer(
    context: Context,
    mediaUrl: String,
    mediaTitle: String
): ExoPlayer {
    return ExoPlayer.Builder(context)
        .apply {
            setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
            setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)
        }
        .build().apply {
            setMediaItem(
                MediaItem.Builder()
                    .apply {
                        setUri(mediaUrl)
                        setMediaMetadata(
                            MediaMetadata.Builder()
                                .setDisplayTitle(mediaTitle)
                                .build()
                        )
                    }
                    .build()
            )
            prepare()
            playWhenReady = true
        }
}

@Composable
fun PlayerControlsSection(
    videoPlayerUiState: UiState<VideoPlayerUiModel>,
    mediaTitle: String,
    isExoplayerPlaying: Boolean,
    onExoPlayerAction: (ExoPlayerAction) -> Unit,
    modifier: Modifier = Modifier,
    onAction: (VideoPlayerAction) -> Unit
) {
    val videoPlayerUiModel = videoPlayerUiState.data ?: VideoPlayerUiModel()
    Box(modifier = modifier.fillMaxSize()) {
        PlayerControls(
            modifier = Modifier.fillMaxSize(),
            videoPlayerUiModel = videoPlayerUiModel,
            title = mediaTitle,
            onPauseToggle = {
                when {
                    isExoplayerPlaying -> {
                        // pause the video
                        onExoPlayerAction(ExoPlayerAction.Pause)
                    }

                    videoPlayerUiModel.playbackState == STATE_ENDED -> {
                        onExoPlayerAction(ExoPlayerAction.SeekTo(0))
                        onExoPlayerAction(ExoPlayerAction.SetPlayWhenReady)
                    }

                    else -> {
                        // play the video
                        // it's already paused
                        onExoPlayerAction(ExoPlayerAction.Play)
                    }
                }
                onAction(VideoPlayerAction.SetIsPlaying(!videoPlayerUiModel.isPlaying))
            },
            onSeekChanged = { timeMs: Float ->
                onExoPlayerAction(ExoPlayerAction.SeekTo(timeMs.toLong()))
            }
        )
    }
}

@Composable
fun PlayerControls(
    title: String,
    videoPlayerUiModel: VideoPlayerUiModel,
    modifier: Modifier = Modifier,
    onPauseToggle: () -> Unit,
    onSeekChanged: (timeMs: Float) -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = videoPlayerUiModel.shouldShowControls,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box {
            TopControl(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(),
                title = title
            )

            CenterControls(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                isPlaying = videoPlayerUiModel.isPlaying,
                onPauseToggle = onPauseToggle,
                playbackState = videoPlayerUiModel.playbackState
            )

            BottomControls(
                modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .animateEnterExit(
                        enter =
                        slideInVertically(
                            initialOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        ),
                        exit =
                        slideOutVertically(
                            targetOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        )
                    ),
                totalDuration = videoPlayerUiModel.totalDuration,
                currentTime = videoPlayerUiModel.currentTime,
                bufferedPercentage = videoPlayerUiModel.bufferedPercentage,
                onSeekChanged = onSeekChanged
            )
        }
    }
}

@Composable
private fun TopControl(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier.padding(VideoPlayerSpacing.LARGE.dp()),
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    playbackState: Int,
    onPauseToggle: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            modifier = Modifier.size(VideoPlayerSize.LARGE.dp()),
            onClick = onPauseToggle
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter =
                when {
                    isPlaying -> {
                        painterResource(id = R.drawable.ic_pause)
                    }

                    playbackState == STATE_ENDED -> {
                        painterResource(id = R.drawable.ic_replay)
                    }

                    else -> {
                        painterResource(id = R.drawable.ic_play)
                    }
                },
                contentDescription = "Play/Pause"
            )
        }
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier = Modifier,
    totalDuration: Long,
    currentTime: Long,
    bufferedPercentage: Int,
    onSeekChanged: (timeMs: Float) -> Unit
) {
    Column(modifier = modifier.padding(bottom = VideoPlayerSpacing.EXTRA_LARGE.dp())) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Slider(
                value = bufferedPercentage.toFloat(),
                enabled = false,
                onValueChange = { /*do nothing*/ },
                valueRange = 0f..100f,
                colors =
                SliderDefaults.colors(
                    disabledThumbColor = Color.Transparent,
                    disabledActiveTrackColor = Color.Gray
                )
            )

            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = currentTime.toFloat(),
                onValueChange = onSeekChanged,
                valueRange = 0f..totalDuration.toFloat(),
                colors =
                SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTickColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = VideoPlayerSpacing.MEDIUM.dp()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = VideoPlayerSpacing.MEDIUM.dp()),
                text = "${currentTime.toMinutesAndSeconds()} - ${totalDuration.formatMinSec()}",
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(
                modifier = Modifier.padding(start = VideoPlayerSpacing.MEDIUM.dp()),
                onClick = {}
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_full_screen),
                    contentDescription = "Enter/Exit fullscreen"
                )
            }
        }
    }
}


const val PLAYER_SEEK_BACK_INCREMENT = 5 * 1000L // 5 seconds
const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L // 10 seconds