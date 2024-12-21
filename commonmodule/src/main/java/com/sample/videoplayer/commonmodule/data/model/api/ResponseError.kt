package com.sample.videoplayer.commonmodule.data.model.api

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
)
