package com.kpaas.plog.domain.entity

data class ReportContentEntity (
    val reportId: Long,
    val reportImgUrl: String,
    val roadAddr: String,
    val reportStatus: String,
    val bookmarkCount: Int,
    val createdAt: String,
    val reportDesc: String,
    val bookmarkedByUser: Boolean
)