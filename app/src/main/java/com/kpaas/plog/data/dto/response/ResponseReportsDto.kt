package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseReportsDto (
    @SerialName("reports") val reports: List<Reports>
)

@Serializable
data class Reports (
    @SerialName("reportId") val reportId: Long,
    @SerialName("reportImgUrl") val reportImgUrl: String,
    @SerialName("reportDesc") val reportDesc: String,
    @SerialName("roadAddr") val roadAddr: String,
    @SerialName("reportStatus") val reportStatus: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("bookmarkCount") val bookmarkCount: Int,
    @SerialName("bookmarkedByUser") val bookmarkedByUser: Boolean
)