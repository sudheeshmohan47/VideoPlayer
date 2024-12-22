package com.sample.videoplayer.datastoragemodule.utils

import java.util.Locale

/**
 * Returns the file extension or an empty string if there is no
 * extension. This method is a convenience method for obtaining the
 * extension of a URL and has undefined results for other Strings.
 * @param url
 * @return The file extension of the given URL.
 */
fun getFileExtensionFromUrl(url: String?): String {
    if (!url.isNullOrEmpty()) {
        var processedUrl = url

        val fragmentIndex = processedUrl.lastIndexOf('#')
        if (fragmentIndex > 0) {
            processedUrl = processedUrl.substring(0, fragmentIndex)
        }

        val queryIndex = processedUrl.lastIndexOf('?')
        if (queryIndex > 0) {
            processedUrl = processedUrl.substring(0, queryIndex)
        }

        val filenamePos = processedUrl.lastIndexOf('/')
        val filename = if (filenamePos >= 0) {
            processedUrl.substring(filenamePos + 1)
        } else {
            processedUrl
        }

        // Check if the filename contains only valid characters
        if (filename.isNotEmpty() && filename.matches(Regex("[a-zA-Z_0-9.\\-()%]+"))) {
            val dotPos = filename.lastIndexOf('.')
            if (dotPos >= 0) {
                return filename.substring(dotPos + 1).lowercase(Locale.US)
            }
        }
    }
    return ""
}
