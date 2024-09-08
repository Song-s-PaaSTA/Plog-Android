package com.kpaas.plog.domain.entity

data class ReportContentEntity (
    val address: String,
    val progress: String,
    val bookmarkCount: Int,
    val date: String,
    val description: String,
    val isBookmark: Boolean
)