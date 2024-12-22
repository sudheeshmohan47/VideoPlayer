package com.sample.videoplayer.datastoragemodule.data.download

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

/**
 * Downloads a file from a given URL and saves it in the app's cache directory under a
 * "downloads" subfolder.
 * The file is optionally named based on the provided filename or
 * assigned a unique name if none is provided.
 *
 * @param context The context of the application, used to access the cache directory.
 * @param url The URL of the file to download.
 * @param mimeType The MIME type of the file (used to determine the file extension
 * if no filename is provided).
 * @param filename The optional name for the file. If null,
 * a unique name is generated based on the current time and MIME type.
 * @return The URI of the downloaded file if successful, or null if an error occurs.
 *
 * @throws IOException If an error occurs while downloading or saving the file.
 */
fun downloadFileFromUri(context: Context, url: String, mimeType: String, filename: String?): Uri? {

    // Create a unique filename if none is provided
    val uniqueFilename =
        filename ?: "${System.currentTimeMillis()}.${mimeType.substringAfterLast("/")}"

    // Create a "downloads" subfolder inside the cache directory
    val downloadsDir = File(context.cacheDir, "downloads").apply {
        if (!exists()) {
            mkdirs() // Create the directory if it doesn't exist
        }
    }

    // Create the file inside the "downloads" folder
    val cacheFile = File(downloadsDir, uniqueFilename)

    return try {
        URL(url).openStream().use { input ->
            FileOutputStream(cacheFile).use { output ->
                input.copyTo(output, DEFAULT_BUFFER_SIZE)
            }
        }
        cacheFile.toUri() // Return the URI of the cached file
    } catch (e: IOException) {
        Timber.e("Error downloading file: $e")
        null // Return null if an error occurs
    }
}
