package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.PloggingDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.data.dto.response.ResponsePloggingRouteDto
import com.kpaas.plog.data.service.PloggingApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PloggingDataSourceImpl @Inject constructor(
    private val ploggingApiService: PloggingApiService
) : PloggingDataSource {
    override suspend fun getPloggingRoute(requestPloggingRouteDto: RequestPloggingRouteDto): BaseResponse<ResponsePloggingRouteDto> {
        return ploggingApiService.getPloggingRoute(requestPloggingRouteDto)
    }

    override suspend fun postPloggingProof(
        request: RequestBody,
        proofImage: MultipartBody.Part
    ): BaseResponse<String> {
        return ploggingApiService.postPloggingPoof(request, proofImage)
    }


}