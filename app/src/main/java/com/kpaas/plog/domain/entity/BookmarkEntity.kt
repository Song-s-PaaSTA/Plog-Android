package com.kpaas.plog.domain.entity

data class BookmarkEntity (
    val reportId: Long,
    val reportImgUrl: String,
    val roadAddr: String,
    val reportStatus: String,
    val bookmarkCount: Int,
)
