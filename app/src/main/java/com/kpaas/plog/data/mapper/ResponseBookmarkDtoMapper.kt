package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.Bookmark
import com.kpaas.plog.domain.entity.BookmarkEntity

fun Bookmark.toBookmarkEntity() = BookmarkEntity(
    reportId = reportId,
    reportImgUrl = reportImgUrl,
    roadAddr = roadAddr,
    reportStatus = reportStatus,
    bookmarkCount = bookmarkCount,
)