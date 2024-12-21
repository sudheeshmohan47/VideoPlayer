package com.sample.videoplayer.commonmodule.data.model.api

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("error")
    val responseError: ResponseError?,
)
