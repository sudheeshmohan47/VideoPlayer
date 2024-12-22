package com.sample.videoplayer.commonmodule.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.annotation.StringRes
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Retrieves a string from the provided string resource ID.
 *
 * @param context The Context used to access resources.
 * @param stringResourceId The resource ID of the string to retrieve.
 * @return The string associated with the resource ID, or an empty string if the resource is not found.
 *
 * Logs an error if the string resource is not found.
 */
fun getStringFromId(context: Context, @StringRes stringResourceId: Int): String {
    try {
        return context.getString(stringResourceId)
    } catch (e: NotFoundException) {
        Log.e(TagError, "String resource not found for ID: $stringResourceId")
    }
    // Return an empty string as a fallback
    return ""
}

/**
 * Formats a given duration in milliseconds into a "MM:SS" string format.
 * If the duration is zero or negative, it returns "..." as a placeholder.
 *
 * @receiver The duration in milliseconds to format.
 * @return A string representing the duration in "MM:SS" format.
 */
fun Long.formatMinSec(): String {
    return if (this <= 0L) {
        "..."
    } else {
        String.format(
            Locale.getDefault(), // Explicitly specify the locale
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) % SECONDS_IN_A_MINUTE
        )
    }
}
fun Long.toMinutesAndSeconds(): String {
    if (this <= 0L) return "00:00"

    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}
