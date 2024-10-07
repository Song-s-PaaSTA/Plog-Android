package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.service.PloggingApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PloggingDataSourceImpl @Inject constructor(
    private val ploggingApiService: PloggingApiService
) : PloggingApiService {
    override suspend fun postPloggingPoof(
        startRoadAddr: RequestBody,
        endRoadAddr: RequestBody,
        ploggingTime: RequestBody,
        proofImage: MultipartBody.Part
    ): BaseResponse<String> {
        return ploggingApiService.postPloggingPoof(startRoadAddr, endRoadAddr, ploggingTime, proofImage)
    }

}