package com.sample.videoplayer.domain.downloads

import com.sample.videoplayer.commonmodule.domain.model.DomainResponse
import com.sample.videoplayer.datastoragemodule.data.database.model.DownloadedFiles

interface DownloadsRepository {
    suspend fun loadDownloadedMediaFiles(): DomainResponse<List<DownloadedFiles>>
}
