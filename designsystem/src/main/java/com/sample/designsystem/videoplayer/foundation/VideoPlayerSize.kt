package com.sample.designsystem.videoplayer.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Enum class representing different sizing options with corresponding dp values
enum class VideoPlayerSize {
    NONE,
    EXTRA_SMALL,
    SMALL,
    MEDIUM,
    MEDIUM_PLUS,
    LARGE,
    XL,
    XXL,
    XXXL
}

/**
 * Extension function to convert VideoPlayerSize to dp
 *
 * Usage:
 * val mediumSize: Dp = VideoPlayerSize.MEDIUM.dp()
 *
 * @return the corresponding Dp value for the VideoPlayerSize
 */
fun VideoPlayerSize.dp(): Dp =
    when (this) {
        VideoPlayerSize.NONE -> 0.dp
        VideoPlayerSize.EXTRA_SMALL -> 36.dp
        VideoPlayerSize.SMALL -> 48.dp
        VideoPlayerSize.MEDIUM -> 56.dp
        VideoPlayerSize.MEDIUM_PLUS -> 64.dp
        VideoPlayerSize.LARGE -> 76.dp
        VideoPlayerSize.XL -> 84.dp
        VideoPlayerSize.XXL -> 96.dp
        VideoPlayerSize.XXXL -> 112.dp
    }

/**
 * Enum class representing different scale sizes used in the UI.
 *
 * Each constant corresponds to a specific scale factor, represented as a float value.
 * This is used to uniformly scale UI components to various sizes.
 *
 * @property value The scale factor as a float value.
 */
enum class VideoPlayerScaleSize(val value: Float) {
    QUARTER(0.25f),
    HALF(0.5f),
    THREE_QUARTERS(0.75f),
    ONE(1f),
    ONE_AND_A_QUARTER(1.25f),
    ONE_AND_A_HALF(1.5f),
    ONE_AND_THREE_QUARTERS(1.75f),
    TWO(2f),
    TWO_AND_A_QUARTER(2.25f),
    TWO_AND_A_HALF(2.5f)
}
