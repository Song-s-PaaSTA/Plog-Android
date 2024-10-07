package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchDto (
    @SerialName("searchedPlace") val searchedPlace: List<Place>
)

@Serializable
data class Place(
    @SerialName("latitude") val latitude: Long,
    @SerialName("longitude") val longitude: Float,
    @SerialName("roadAddr") val roadAddr: String,
    @SerialName("placeInfo") val placeInfo: String
)