package com.sample.videoplayer.domain.home

import com.sample.videoplayer.commonmodule.domain.model.DomainResponse
import com.sample.videoplayer.domain.model.MediaFile

class HomeUseCase(private val homeRepository: HomeRepository) {
    suspend fun loadMediaFiles(): DomainResponse<List<MediaFile>> {
        val mediaFiles = homeRepository.loadMediaFiles().data
        mediaFiles?.let {
            return DomainResponse(it.map { mediaFile ->
                MediaFile(mediaFile.title, mediaFile.thumbnailUrl, mediaFile.videoUrl, mediaFile.description)
            })
        } ?: return DomainResponse(emptyList())
    }
}
