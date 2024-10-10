package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUpDto (
    @SerialName("nickname") val nickname: String,
    @SerialName("email") val email: String,
    @SerialName("profileImageUrl") val profileImageUrl: String,
    @SerialName("score") val score: Int,
)