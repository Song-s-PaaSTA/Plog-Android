package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.Place
import com.kpaas.plog.domain.entity.SearchResultListEntity

fun Place.toSearchResultListEntity() = SearchResultListEntity(
    latitude = latitude,
    longitude = longitude,
    placeInfo = placeInfo,
    roadAddr = roadAddr
)