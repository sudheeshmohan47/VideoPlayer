package com.sample.videoplayer.commonmodule.utils

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.sample.videoplayer.commonmodule.domain.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Handles displaying an error message in a Snackbar.
 * If the error message is empty, it retrieves a message from a resource ID.
 *
 * @param context The context for accessing resources.
 * @param snackBarHostState The host state for displaying the Snackbar.
 * @param coroutineScope The CoroutineScope for launching the Snackbar display.
 * @param errorMessage The error message containing either a string or a resource ID.
 * @param onSnackbarDisplayed Optional callback invoked when the Snackbar is displayed.
 */
fun handleErrorMessage(
    context: Context,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    errorMessage: Message,
    onSnackbarDisplayed: (() -> Unit)? = null
) {
    val message =
        errorMessage.message.ifEmpty {
            getStringFromId(
                context, errorMessage.messageResId
            )
        }
    showSnackBar(
        coroutineScope = coroutineScope,
        message = message,
        snackBarHostState = snackBarHostState,
        onSnackbarDisplayed = onSnackbarDisplayed
    )
}

// Snackbar
// Method to show a snackbar when associated with user interaction
fun showSnackBar(
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    message: String?,
    actionText: String? = null,
    onActionPerformed: (() -> Unit)? = null,
    onSnackbarDisplayed: (() -> Unit)? = null
) {
    coroutineScope.launch {
        message?.let { nonNullMessage ->
            val snackbarResult = snackBarHostState.showSnackbar(
                message = nonNullMessage,
                withDismissAction = true,
                actionLabel = actionText,
                duration = SnackbarDuration.Short,
            )
            onSnackbarDisplayed?.invoke()
            when (snackbarResult) {
                SnackbarResult.ActionPerformed, SnackbarResult.Dismissed -> {
                    onActionPerformed?.invoke()
                }
            }
        }
    }
}
