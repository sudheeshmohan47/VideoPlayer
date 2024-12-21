package com.sample.designsystem.videoplayer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sample.designsystem.videoplayer.foundation.VideoPlayerSpacing
import com.sample.designsystem.videoplayer.foundation.dp

private const val DialogWidthPercentage = 0.9f

@Composable
fun VideoPlayerCustomDialog(
    title: String,
    description: String,
    negativeButtonLabel: String,
    positiveButtonLabel: String,
    onPositiveButtonClick: () -> Unit,
    dialogType: AlertDialogType = AlertDialogType.SHEET,
    onDismiss: () -> Unit
) {
    when (dialogType) {
        AlertDialogType.SHEET -> {
            VideoPlayerBottomSheetDialog(
                title = title,
                description = description,
                negativeButtonLabel = negativeButtonLabel,
                positiveButtonLabel = positiveButtonLabel,
                onPositiveButtonClick = onPositiveButtonClick,
                onDismiss = onDismiss
            )
        }

        AlertDialogType.DIALOG -> {
            VideoPlayerCustomAlertDialog(
                title = title,
                description = description,
                negativeButtonLabel = negativeButtonLabel,
                positiveButtonLabel = positiveButtonLabel,
                onPositiveButtonClick = onPositiveButtonClick,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
private fun VideoPlayerCustomAlertDialog(
    title: String,
    description: String,
    negativeButtonLabel: String,
    positiveButtonLabel: String,
    onPositiveButtonClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            Modifier
                .fillMaxWidth(DialogWidthPercentage)
                .background(Color.Transparent)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(VideoPlayerSpacing.LARGE.dp()))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(VideoPlayerSpacing.LARGE.dp())
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(VideoPlayerSpacing.MEDIUM.dp()))

                // Description
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(VideoPlayerSpacing.LARGE.dp()))

                // Button Section
                Row {
                    VideoPlayerButton(
                        label = negativeButtonLabel,
                        variant = VideoPlayerButtonVariant.OUTLINED,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onDismiss()
                        }
                    )
                    Spacer(modifier = Modifier.width(VideoPlayerSpacing.MEDIUM.dp()))
                    VideoPlayerButton(
                        label = positiveButtonLabel,
                        variant = VideoPlayerButtonVariant.PRIMARY,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onDismiss()
                            onPositiveButtonClick()
                        }
                    )
                }
            }
        }
    }
}

enum class AlertDialogType {
    SHEET, DIALOG
}
