package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRewardDto(
    @SerialName("rewards") val rewards: List<Reward>
)

@Serializable
data class Reward(
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImageUrl") val profileImageUrl: String,
    @SerialName("score") val score: Int
)