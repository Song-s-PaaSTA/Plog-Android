package com.kpaas.plog.domain.repository

import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.domain.entity.LatLngEntity
import java.io.File

interface PloggingRepository {
    suspend fun getPloggingRoute(
        requestPloggingRouteDto: RequestPloggingRouteDto
    ): Result<List<LatLngEntity>>

    suspend fun postPloggingProof(
        startRoadAddr: String,
        endRoadAddr: String,
        ploggingTime: String,
        proofImage: File
    ): Result<String>
}