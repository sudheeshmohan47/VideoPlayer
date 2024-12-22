package com.sample.videoplayer.domain.home

import com.sample.videoplayer.commonmodule.domain.model.DomainResponse
import com.sample.videoplayer.data.model.MediaFileResponse

interface HomeRepository {
    suspend fun loadMediaFiles(): DomainResponse<List<MediaFileResponse>>
}
