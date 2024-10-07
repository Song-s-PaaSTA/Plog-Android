package com.kpaas.plog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTrashDto (
    @SerialName("trashPlaces") val trashPlaces: List<Trash>
)

@Serializable
data class Trash(
    @SerialName("id") val id: Int,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("roadAddr") val roadAddr: String,
    @SerialName("placeInfo") val placeInfo: String,
)