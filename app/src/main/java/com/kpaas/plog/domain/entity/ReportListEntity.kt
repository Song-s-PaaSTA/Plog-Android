package com.kpaas.plog.domain.entity

data class ReportListEntity (
    val reportId: Long,
    val reportImgUrl: String,
    val roadAddr: String,
    val reportDesc: String,
    val reportStatus: String,
    val bookmarkCount: Int,
    val bookmarkedByUser: Boolean,
)