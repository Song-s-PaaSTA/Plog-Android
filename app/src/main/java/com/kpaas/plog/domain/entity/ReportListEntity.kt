package com.kpaas.plog.domain.entity

data class ReportListEntity (
    val id: Int,
    val title: String,
    val content: String,
    val progress: String,
    val bookmarkCount: Int,
)