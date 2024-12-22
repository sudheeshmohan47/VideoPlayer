package com.sample.videoplayer.datastoragemodule.data.download

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.net.URL

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
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if an error occurs
    }
}
