package com.sample.designsystem.videoplayer.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Enum class representing different spacing options
enum class VideoPlayerSpacing {
    NONE,
    EXTRA_SMALL,
    SMALL,
    MEDIUM,
    MEDIUM_PLUS,
    LARGE,
    EXTRA_LARGE
}

/**
 * This function returns the Spacing in dp
 * Usage -> VideoPlayerSpacing.MEDIUM.dp()
 */
fun VideoPlayerSpacing.dp(): Dp =
    when (this) {
        VideoPlayerSpacing.NONE -> 0.dp
        VideoPlayerSpacing.EXTRA_SMALL -> 4.dp
        VideoPlayerSpacing.SMALL -> 8.dp
        VideoPlayerSpacing.MEDIUM -> 16.dp
        VideoPlayerSpacing.MEDIUM_PLUS -> 20.dp
        VideoPlayerSpacing.LARGE -> 24.dp
        VideoPlayerSpacing.EXTRA_LARGE -> 30.dp
    }
