package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseMyBookmarksDto
import com.kpaas.plog.data.dto.response.ResponseMyReportsDto
import com.kpaas.plog.data.dto.response.ResponseReportDetailDto
import com.kpaas.plog.data.dto.response.ResponseReportsDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.BOOKMARKS
import com.kpaas.plog.data.service.ApiKeyStorage.MINE
import com.kpaas.plog.data.service.ApiKeyStorage.REPORTS
import com.kpaas.plog.data.service.ApiKeyStorage.REPORT_ID
import com.kpaas.plog.data.service.ApiKeyStorage.REPORT_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportApiService {
    @GET("/$REPORT_SERVICE/$API/$V1/$REPORTS")
    suspend fun getReports(
        @Query("region") region: List<String>? = null,
        @Query("sort") sort: String? = null,
        @Query("status") status: List<String>? = null,
    ): BaseResponse<ResponseReportsDto>

    @GET("/$REPORT_SERVICE/$API/$V1/$REPORTS/{$REPORT_ID}")
    suspend fun getReportDetail(
        @Path("reportId") reportId: Long
    ): BaseResponse<ResponseReportDetailDto>

    @Multipart
    @POST("/$REPORT_SERVICE/$API/$V1/$REPORTS")
    suspend fun postReport(
        @PartMap requestDto: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part reportImgFile: MultipartBody.Part
    ): BaseResponse<Unit>

    @Multipart
    @PATCH("/$REPORT_SERVICE/$API/$V1/$REPORTS")
    suspend fun patchReport(
        @PartMap requestDto: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part reportImgFile: MultipartBody.Part
    ): BaseResponse<Unit>

    @DELETE("/$REPORT_SERVICE/$API/$V1/$REPORTS/{$REPORT_ID}")
    suspend fun deleteReport(
        @Path("reportId") reportId: Long
    ): BaseResponse<Unit>

    @GET("/$REPORT_SERVICE/$API/$V1/$REPORTS/$MINE")
    suspend fun getMyReports(): BaseResponse<ResponseMyReportsDto>

    @GET("/$REPORT_SERVICE/$API/$V1/$REPORTS/$BOOKMARKS/$MINE")
    suspend fun getMyBookmarks(): BaseResponse<ResponseMyBookmarksDto>

    @POST("/$REPORT_SERVICE/$API/$V1/$REPORTS/{$REPORT_ID}/$BOOKMARKS")
    suspend fun postBookmark(
        @Path("reportId") reportId: Long
    ): BaseResponse<Unit>
}