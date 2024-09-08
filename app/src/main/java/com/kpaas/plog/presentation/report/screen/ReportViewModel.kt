package com.kpaas.plog.presentation.report.screen

import androidx.lifecycle.ViewModel
import com.kpaas.plog.domain.entity.ReportListEntity

class ReportViewModel: ViewModel() {
    val mockReports = listOf(
        ReportListEntity(
            id = 1,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 시작 전",
            bookmarkCount = 12,
        ),
        ReportListEntity(
            id = 2,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 중",
            bookmarkCount = 12,
        ),
        ReportListEntity(
            id = 3,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 완료",
            bookmarkCount = 12,
        ),
        ReportListEntity(
            id = 4,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 시작 전",
            bookmarkCount = 12,
        ),
        ReportListEntity(
            id = 5,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 중",
            bookmarkCount = 12,
        ),
        ReportListEntity(
            id = 6,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 완료",
            bookmarkCount = 12,
        ),
        ReportListEntity(
            id = 7,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 시작 전",
            bookmarkCount = 15,
        ),
        ReportListEntity(
            id = 8,
            title = "서울 노원구 동일로 1058",
            content = "2층 젠카츠 공릉본점",
            progress = "청소 중",
            bookmarkCount = 15,
        ),
    )
}