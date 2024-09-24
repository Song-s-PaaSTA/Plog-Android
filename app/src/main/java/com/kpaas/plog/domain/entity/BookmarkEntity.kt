package com.kpaas.plog.domain.entity

data class BookmarkEntity (
    val id: Int,
    val title: String,
    val content: String,
    val progress: String,
    val bookmarkCount: Int,
    val isBookmark: Boolean,
)
