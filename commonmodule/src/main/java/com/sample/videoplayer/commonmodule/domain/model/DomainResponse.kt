package com.sample.videoplayer.commonmodule.domain.model

import com.sample.videoplayer.commonmodule.domain.exception.DomainException

/**
 * Wrapper object for response
 */
data class DomainResponse<T>(
    val data: T? = null,
    val error: DomainException? = null,
    val errorMessage: String? = null
)
