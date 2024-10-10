package com.kpaas.plog.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPloggingRouteDto (
    @SerialName("startX") val startX: Double,
    @SerialName("startY") val startY: Double,
    @SerialName("endX") val endX: Double,
    @SerialName("endY") val endY: Double,
    @SerialName("passX") val passX: Double,
    @SerialName("passY") val passY: Double,
    @SerialName("reqCoordType") val reqCoordType: String,
    @SerialName("startName") val startName: String,
    @SerialName("endName") val endName: String,
    @SerialName("resCoordType") val resCoordType: String,
)