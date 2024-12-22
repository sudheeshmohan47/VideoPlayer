package com.sample.videoplayer.data.home

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.videoplayer.commonmodule.domain.exception.mapException
import com.sample.videoplayer.commonmodule.domain.model.DomainResponse
import com.sample.videoplayer.data.model.MediaFileResponse
import com.sample.videoplayer.domain.home.HomeRepository
import dagger.hilt.android.qualifiers.ApplicationContext

class HomeService(@ApplicationContext private val context: Context) : HomeRepository {
    override suspend fun loadMediaFiles(): DomainResponse<List<MediaFileResponse>> {
        var domainResponse: DomainResponse<List<MediaFileResponse>>
        try {
            val json =
                context.assets.open("sample_videos.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val mediaFiles: List<MediaFileResponse> =
                gson.fromJson(json, object : TypeToken<List<MediaFileResponse>>() {}.type)
            mediaFiles.let {
                domainResponse = DomainResponse(mediaFiles)
            }
        } catch (e: Exception) {
            throw mapException(e)
        }
        return domainResponse
    }
}
