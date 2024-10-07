package com.kpaas.plog.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code") val code: Int,
    @SerialName("content") val content: T? = null
)