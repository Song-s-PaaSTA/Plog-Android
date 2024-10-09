package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.ReportDetail
import com.kpaas.plog.data.dto.response.Reports
import com.kpaas.plog.domain.entity.ReportContentEntity
import com.kpaas.plog.domain.entity.ReportListEntity

fun Reports.toReportListEntity() = ReportListEntity(
    reportId = reportId,
    reportImgUrl = reportImgUrl,
    roadAddr = roadAddr,
    reportStatus = reportStatus,
    bookmarkCount = bookmarkCount,
    bookmarkedByUser = bookmarkedByUser
)

fun ReportDetail.toReportContentEntity() = ReportContentEntity(
    reportId = reportId,
    reportImgUrl = reportImgUrl,
    roadAddr = roadAddr,
    reportDesc = reportDesc,
    reportStatus = reportStatus,
    bookmarkCount = bookmarkCount,
    createdAt = createdAt,
    bookmarkedByUser = bookmarkedByUser
)