package com.sample.designsystem.videoplayer.foundation

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class VideoPlayerTextSpacing {
    NONE,
    EXTRA_SMALL,
    SMALL,
    MEDIUM,
    MEDIUM_PLUS,
    LARGE,
    EXTRA_LARGE
}

/**
 * This function returns the Text Spacing in sp
 * Usage -> VideoPlayerTextSpacing.MEDIUM.sp()
 */
fun VideoPlayerTextSpacing.sp(): TextUnit =
    when (this) {
        VideoPlayerTextSpacing.NONE -> 0.sp
        VideoPlayerTextSpacing.EXTRA_SMALL -> 0.10.sp
        VideoPlayerTextSpacing.SMALL -> 0.20.sp
        VideoPlayerTextSpacing.MEDIUM -> 0.30.sp
        VideoPlayerTextSpacing.MEDIUM_PLUS -> 0.40.sp
        VideoPlayerTextSpacing.LARGE -> 0.50.sp
        VideoPlayerTextSpacing.EXTRA_LARGE -> 0.60.sp
    }
