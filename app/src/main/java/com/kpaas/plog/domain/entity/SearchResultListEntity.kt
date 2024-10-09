package com.kpaas.plog.domain.entity

data class SearchResultListEntity(
    val latitude: Double,
    val longitude: Double,
    val placeInfo: String,
    val roadAddr: String,
)