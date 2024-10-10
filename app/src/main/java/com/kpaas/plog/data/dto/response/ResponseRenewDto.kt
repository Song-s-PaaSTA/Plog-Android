package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRenewDto (
    @SerialName("accessToken") val accessToken: String,
)