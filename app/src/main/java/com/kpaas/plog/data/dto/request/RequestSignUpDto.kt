package com.kpaas.plog.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUpDto (
    @SerialName("nickname") val nickname: String
)