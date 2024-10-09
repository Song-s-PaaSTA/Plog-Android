package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.ReportDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseMyBookmarksDto
import com.kpaas.plog.data.dto.response.ResponseMyReportsDto
import com.kpaas.plog.data.dto.response.ResponseReportDetailDto
import com.kpaas.plog.data.dto.response.ResponseReportsDto
import com.kpaas.plog.data.service.ReportApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportApiService: ReportApiService
) : ReportDataSource {
    override suspend fun getReports(
        region: List<String>?,
        sort: String?,
        status: List<String>?
    ): BaseResponse<ResponseReportsDto> {
        return reportApiService.getReports(region, sort, status)
    }

    override suspend fun getReportDetail(reportId: Long): BaseResponse<ResponseReportDetailDto> {
        return reportApiService.getReportDetail(reportId)
    }

    override suspend fun postReport(
        requestDto: Map<String, RequestBody>,
        reportImgFile: MultipartBody.Part
    ): BaseResponse<Unit> {
        return reportApiService.postReport(requestDto, reportImgFile)
    }

    override suspend fun patchReport(
        reportId: Long,
        requestDto: Map<String, RequestBody>,
        reportImgFile: MultipartBody.Part?
    ): BaseResponse<Unit> {
        return reportApiService.patchReport(reportId, requestDto, reportImgFile)
    }

    override suspend fun deleteReport(reportId: Long): BaseResponse<Unit> {
        return reportApiService.deleteReport(reportId)
    }

    override suspend fun getMyReports(): BaseResponse<ResponseMyReportsDto> {
        return reportApiService.getMyReports()
    }

    override suspend fun getMyBookmarks(): BaseResponse<ResponseMyBookmarksDto> {
        return reportApiService.getMyBookmarks()
    }

    override suspend fun postBookmark(reportId: Long): BaseResponse<Unit> {
        return reportApiService.postBookmark(reportId)
    }
}