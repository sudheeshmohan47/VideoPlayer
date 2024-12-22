package com.sample.videoplayer.data.downloads

import com.sample.videoplayer.commonmodule.domain.model.DomainResponse
import com.sample.videoplayer.datastoragemodule.data.database.dao.DownloadedFilesDao
import com.sample.videoplayer.datastoragemodule.data.database.model.DownloadedFiles
import com.sample.videoplayer.domain.downloads.DownloadsRepository

class DownloadsService(private val downloadedFilesDao: DownloadedFilesDao) : DownloadsRepository {

    override suspend fun loadDownloadedMediaFiles(): DomainResponse<List<DownloadedFiles>> {
        val downloadedMediaFiles = downloadedFilesDao.getDownloadedFiles()
        return DomainResponse(downloadedMediaFiles)
    }
}
