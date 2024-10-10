package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseMyBookmarksDto
import com.kpaas.plog.data.dto.response.ResponseMyReportsDto
import com.kpaas.plog.data.dto.response.ResponseReportDetailDto
import com.kpaas.plog.data.dto.response.ResponseReportsDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ReportDataSource {
    suspend fun getReports(
        region: List<String>?,
        sort: String?,
        status: List<String>?,
    ): BaseResponse<ResponseReportsDto>

    suspend fun getReportDetail(reportId: Long): BaseResponse<ResponseReportDetailDto>

    suspend fun postReport(
        requestDto: RequestBody,
        reportImgFile: MultipartBody.Part
    ): BaseResponse<Unit>

    suspend fun patchReport(
        reportId: Long,
        requestDto: RequestBody,
        reportImgFile: MultipartBody.Part?
    ): BaseResponse<Unit>

    suspend fun deleteReport(reportId: Long): BaseResponse<Unit>

    suspend fun getMyReports(): BaseResponse<ResponseMyReportsDto>

    suspend fun getMyBookmarks(): BaseResponse<ResponseMyBookmarksDto>

    suspend fun postBookmark(reportId: Long): BaseResponse<Unit>
}