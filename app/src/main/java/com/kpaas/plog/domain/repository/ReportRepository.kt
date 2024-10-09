package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.BookmarkEntity
import com.kpaas.plog.domain.entity.MyReportListEntity
import com.kpaas.plog.domain.entity.ReportContentEntity
import com.kpaas.plog.domain.entity.ReportListEntity
import java.io.File

interface ReportRepository {
    suspend fun getReports(
        region: List<String>?,
        sort: String?,
        status: List<String>?,
    ): Result<List<ReportListEntity>>

    suspend fun getReportDetail(reportId: Long): Result<ReportContentEntity>

    suspend fun postReport(
        reportDesc: String,
        roadAddr: String,
        reportStatus: String,
        reportImgUrl: File,
    ): Result<Unit>

    suspend fun patchReport(
        inputReportStatus: String,
        reportDesc: String,
        existingImgUrl: File
    ): Result<Unit>

    suspend fun deleteReport(reportId: Long): Result<Unit>

    suspend fun getMyReports(): Result<List<MyReportListEntity>>

    suspend fun getMyBookmarks(): Result<List<BookmarkEntity>>

    suspend fun postBookmark(reportId: Long): Result<Unit>
}