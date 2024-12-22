package com.sample.videoplayer.domain.downloads

import com.sample.videoplayer.commonmodule.domain.model.DomainResponse
import com.sample.videoplayer.domain.model.MediaFile

class DownloadsUseCase(private val downloadsRepository: DownloadsRepository) {
    suspend fun loadDownloadedMediaFiles(): DomainResponse<List<MediaFile>> {
        val mediaFiles = downloadsRepository.loadDownloadedMediaFiles().data
        mediaFiles?.let {
            return DomainResponse(
                it.map { downloadedFile ->
                    MediaFile(
                        downloadedFile.title,
                        "",
                        downloadedFile.localPath,
                        downloadedFile.description
                    )
                }
            )
        } ?: return DomainResponse(emptyList())
    }
}
