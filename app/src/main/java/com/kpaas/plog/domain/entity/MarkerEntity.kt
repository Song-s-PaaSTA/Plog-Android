package com.kpaas.plog.domain.entity

data class MarkerEntity (
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val caption: String,
    val subCaption: String
)