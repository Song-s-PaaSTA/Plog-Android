package com.kpaas.plog.domain.entity

data class MarkerEntity (
    val placeId: Int,
    val latitude: Double,
    val longitude: Double,
    val roadAddr: String,
    val placeInfo: String? = null
)