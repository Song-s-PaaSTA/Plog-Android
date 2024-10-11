package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.data.dto.response.ResponsePloggingRouteDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PloggingDataSource {
    suspend fun postPloggingRoute(
        requestPloggingRouteDto: RequestPloggingRouteDto
    ): BaseResponse<ResponsePloggingRouteDto>

    suspend fun postPloggingProof(
        request: RequestBody,
        file: MultipartBody.Part?
    ): BaseResponse<String?>
}