package com.kpaas.plog.data.mapper

import com.kpaas.plog.domain.entity.LatLngEntity

fun List<Double>.toLatLngEntity() = LatLngEntity(
    latitude = this[0],
    longitude = this[1]
)