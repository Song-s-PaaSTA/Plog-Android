package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyReportsDto (
    @SerialName("myReports") val myReports: List<MyReport>
)

@Serializable
data class MyReport(
    @SerialName("reportId") val reportId: Long,
    @SerialName("reportImgUrl") val reportImgUrl: String,
    @SerialName("roadAddr") val roadAddr: String
)