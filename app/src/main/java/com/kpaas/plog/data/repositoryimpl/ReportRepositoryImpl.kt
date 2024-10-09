package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.ReportDataSource
import com.kpaas.plog.data.mapper.toBookmarkEntity
import com.kpaas.plog.data.mapper.toMyReportListEntity
import com.kpaas.plog.data.mapper.toReportContentEntity
import com.kpaas.plog.data.mapper.toReportListEntity
import com.kpaas.plog.domain.entity.BookmarkEntity
import com.kpaas.plog.domain.entity.MyReportListEntity
import com.kpaas.plog.domain.entity.ReportContentEntity
import com.kpaas.plog.domain.entity.ReportListEntity
import com.kpaas.plog.domain.repository.ReportRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource
) : ReportRepository {
    override suspend fun getReports(
        region: List<String>?,
        sort: String?,
        status: List<String>?
    ): Result<List<ReportListEntity>> {
        return runCatching {
            reportDataSource.getReports(
                region,
                sort,
                status
            ).message?.reports?.map { it.toReportListEntity() } ?: emptyList()
        }
    }

    override suspend fun getReportDetail(reportId: Long): Result<ReportContentEntity> {
        return runCatching {
            reportDataSource.getReportDetail(reportId).message?.reportDetail?.toReportContentEntity()
                ?: ReportContentEntity(
                    reportId = 0,
                    reportImgUrl = "",
                    roadAddr = "",
                    reportDesc = "",
                    reportStatus = "",
                    bookmarkCount = 0,
                    createdAt = "",
                    bookmarkedByUser = false
                )
        }
    }

    override suspend fun postReport(
        reportDesc: String,
        roadAddr: String,
        reportStatus: String,
        reportImgFile: File
    ): Result<Unit> {
        return runCatching {
            val reportDescBody = reportDesc.toRequestBody("text/plain".toMediaTypeOrNull())
            val roadAddrBody = roadAddr.toRequestBody("text/plain".toMediaTypeOrNull())
            val reportStatusBody = reportStatus.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestDtoMap = mapOf(
                reportDesc to reportDescBody,
                roadAddr to roadAddrBody,
                reportStatus to reportStatusBody
            )
            val filePart = reportImgFile.let {
                val requestBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("reportImgFile", it.name, requestBody)
            }
            reportDataSource.postReport(
                requestDtoMap,
                filePart
            ).message
        }
    }

    override suspend fun patchReport(
        reportId: Long,
        reportStatus: String,
        reportDesc: String,
        existingImgUrl: String,
        reportImgFile: File?
    ): Result<Unit> {
        return runCatching {
            val reportStatusBody = reportStatus.toRequestBody("text/plain".toMediaTypeOrNull())
            val reportDescBody = reportDesc.toRequestBody("text/plain".toMediaTypeOrNull())
            val existingImgUrlBody = existingImgUrl.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestDtoMap = mapOf(
                reportStatus to reportStatusBody,
                reportDesc to reportDescBody,
                existingImgUrl to existingImgUrlBody
            )
            val filePart = reportImgFile?.let {
                val requestBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("reportImgFile", it.name, requestBody)
            }
            reportDataSource.patchReport(
                reportId,
                requestDtoMap,
                filePart
            ).message
        }
    }

    override suspend fun deleteReport(reportId: Long): Result<Unit> {
        return runCatching { reportDataSource.deleteReport(reportId) }
    }

    override suspend fun getMyReports(): Result<List<MyReportListEntity>> {
        return runCatching {
            reportDataSource.getMyReports().message?.myReports?.map { it.toMyReportListEntity() }
                ?: emptyList()
        }
    }

    override suspend fun getMyBookmarks(): Result<List<BookmarkEntity>> {
        return runCatching {
            reportDataSource.getMyBookmarks().message?.bookmarked?.map { it.toBookmarkEntity() }
                ?: emptyList()
        }
    }

    override suspend fun postBookmark(reportId: Long): Result<Unit> {
        return runCatching {
            reportDataSource.postBookmark(reportId)
        }
    }

}