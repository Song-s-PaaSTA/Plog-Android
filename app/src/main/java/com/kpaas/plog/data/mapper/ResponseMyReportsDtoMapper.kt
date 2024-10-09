package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.MyReport
import com.kpaas.plog.domain.entity.MyReportListEntity

fun MyReport.toMyReportListEntity() = MyReportListEntity(
    reportId = reportId,
    reportImgUrl = reportImgUrl,
    roadAddr = roadAddr,
)