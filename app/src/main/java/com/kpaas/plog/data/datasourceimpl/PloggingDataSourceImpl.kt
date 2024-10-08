package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.PloggingDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.service.PloggingApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PloggingDataSourceImpl @Inject constructor(
    private val ploggingApiService: PloggingApiService
) : PloggingDataSource {
    override suspend fun postPloggingProof(
        startRoadAddr: RequestBody,
        endRoadAddr: RequestBody,
        ploggingTime: RequestBody,
        proofImage: MultipartBody.Part
    ): BaseResponse<String> {
        return ploggingApiService.postPloggingPoof(
            startRoadAddr,
            endRoadAddr,
            ploggingTime,
            proofImage
        )
    }

}