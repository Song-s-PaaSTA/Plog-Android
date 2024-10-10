package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePloggingRouteDto (
    @SerialName("coordinates") val coordinates: List<List<Double>>,
)