package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.Trash
import com.kpaas.plog.domain.entity.MarkerEntity

fun Trash.toMarkerEntity() = MarkerEntity(
    placeId = placeId,
    latitude = latitude,
    longitude = longitude,
    roadAddr = roadAddr,
    placeInfo = placeInfo
)