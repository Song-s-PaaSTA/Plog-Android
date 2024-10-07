package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyPloggingListDto (
    @SerialName("ploggingList") val ploggingList: List<PloggingList>
)

@Serializable
data class PloggingList (
    @SerialName("startRoadAddr") val startRoadAddr: String,
    @SerialName("endRoadAddr") val endRoadAddr: String,
    @SerialName("ploggingTime") val ploggingTime: String,
    @SerialName("ploggingImgUrl") val ploggingImgUrl: String,
)