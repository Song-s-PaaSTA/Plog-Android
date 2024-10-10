package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.data.dto.response.ResponsePloggingRouteDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.PLOGGING
import com.kpaas.plog.data.service.ApiKeyStorage.PLOGGING_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.PROOF
import com.kpaas.plog.data.service.ApiKeyStorage.ROUTE
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface PloggingApiService {
    @GET("/$PLOGGING_SERVICE/$API/$V1/$PLOGGING/$ROUTE")
    suspend fun getPloggingRoute(
        @Body requestPloggingRouteDto: RequestPloggingRouteDto
    ) : BaseResponse<ResponsePloggingRouteDto>

    @Multipart
    @POST("/$PLOGGING_SERVICE/$API/$V1/$PLOGGING/$PROOF")
    suspend fun postPloggingPoof(
        @Part("request") request: RequestBody,
        @Part proofImage: MultipartBody.Part
    ) : BaseResponse<String>
}