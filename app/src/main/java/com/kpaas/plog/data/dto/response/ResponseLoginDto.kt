package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    @SerialName("nickname") val nickname: String? = null,
    @SerialName("email") val email: String,
    @SerialName("socialLoginId") val socialLoginId: String,
    @SerialName("socialLoginType") val socialLoginType: String,
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("isNewMember") val isNewMember: Boolean
)