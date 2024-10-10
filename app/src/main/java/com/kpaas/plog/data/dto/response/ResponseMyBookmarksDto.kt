package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyBookmarksDto (
    @SerialName("bookmarked") val bookmarked: List<Bookmark>
)

@Serializable
data class Bookmark (
    @SerialName("reportId") val reportId: Long,
    @SerialName("reportImgUrl") val reportImgUrl: String,
    @SerialName("reportStatus") val reportStatus: String,
    @SerialName("roadAddr") val roadAddr: String,
    @SerialName("bookmarkCount") val bookmarkCount: Int
)