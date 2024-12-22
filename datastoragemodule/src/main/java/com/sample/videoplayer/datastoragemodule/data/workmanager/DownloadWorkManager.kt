package com.sample.videoplayer.datastoragemodule.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.data.database.domain.model.DownloadStatus
import com.sample.videoplayer.datastoragemodule.data.database.model.DownloadedFiles
import com.sample.videoplayer.datastoragemodule.data.download.downloadFileFromUri
import com.sample.videoplayer.datastoragemodule.utils.getFileExtensionFromUrl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DownloadWorkManager @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val downloadedFilesDao: DownloadedFilesDao,
) :
    CoroutineWorker(context, workerParams) {

    companion object {
        const val TAG = "DownloadWorkManager"

        fun oneTimeWorkRequest(): OneTimeWorkRequest {
            val constrains = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .setRequiresBatteryNotLow(true)
                .build()

            return OneTimeWorkRequestBuilder<DownloadWorkManager>()
                .setConstraints(constrains)
                .addTag(TAG)
                .build()
        }
    }

    override suspend fun doWork(): Result {
        // Get the files to be downloaded from db
        val filesToDownload = downloadedFilesDao.getFilesToDownload()

        if (filesToDownload.isNotEmpty()) {
            handleDownloadingFiles(filesToDownload)
        }
        return Result.success()
    }

    private suspend fun handleDownloadingFiles(filesToDownload: List<DownloadedFiles>) {
        for (item in filesToDownload) {
            val mimeType = when (getFileExtensionFromUrl(item.url)) {
                "mp3" -> "audio/mp3"
                "mp4" -> "video/mp4"
                else -> ""
            }
            val filename = item.title
            val url = item.url
            url.let {
                try {
                    val uri = downloadFileFromUri(context = context, url, mimeType, filename)
                    uri?.let {
                        // add the downloaded file to the database. This will replace existing with the
                        // same url
                        downloadedFilesDao.addDownloadedFile(
                            DownloadedFiles(
                                title = filename,
                                url = url,
                                localPath = uri.toString(),
                                downloadStatus = DownloadStatus.DOWNLOADED.name
                            )
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.failure()
                }
            }
        }
    }
}
