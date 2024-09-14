package com.kpaas.plog.presentation.report.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kpaas.plog.domain.entity.BookmarkEntity

@HiltViewModel
class BookmarkViewModel @Inject constructor() : ViewModel() {

    val mockBookmarkList = listOf(
        BookmarkEntity(
            id = 1,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 시작 전",
            bookmarkCount = 12
        ),
        BookmarkEntity(
            id = 2,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 중",
            bookmarkCount = 15
        ),
        BookmarkEntity(
            id = 3,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 중",
            bookmarkCount = 2
        ),
        BookmarkEntity(
            id = 4,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 완료",
            bookmarkCount = 10
        ),
        BookmarkEntity(
            id = 5,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 시작 전",
            bookmarkCount = 1
        ),
        BookmarkEntity(
            id = 6,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 중",
            bookmarkCount = 19
        ),
        BookmarkEntity(
            id = 7,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 시작 전",
            bookmarkCount = 7
        ),
        BookmarkEntity(
            id = 8,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 완료",
            bookmarkCount = 29
        ),
    )
}
