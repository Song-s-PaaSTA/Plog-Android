package com.kpaas.plog.presentation.report.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kpaas.plog.domain.entity.ReportListEntity
import com.kpaas.plog.util.ChipState

class ReportViewModel: ViewModel() {
    var reportChipStates = mutableStateListOf<ChipState>()
        private set

    var regionChipStates = mutableStateListOf<ChipState>()
        private set

    var progressChipStates = mutableStateListOf<ChipState>()
        private set

    val initialChips = listOf("지역", "최신순", "인기순", "상태")
    val regionChips = listOf("서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "울산광역시",
        "세종특별자치시", "경기도", "강원특별자치도", "충청북도", "충청남도", "전북특별자치도", "전라남도",
        "경상북도", "경상남도", "제주특별자치도")
    val progressChips = listOf("청소 시작 전", "청소 중", "청소 완료")

    init {
        reportChipStates.addAll(
            initialChips.map { chipText ->
                ChipState(chipText, mutableStateOf(false))
            }
        )
        regionChipStates.addAll(
            regionChips.map { chipText ->
                ChipState(chipText, mutableStateOf(false))
            }
        )
        progressChipStates.addAll(
            progressChips.map { chipText ->
                ChipState(chipText, mutableStateOf(false))
            }
        )
    }



    // 특정 Chip을 선택/해제하는 함수
    fun toggleChipSelection(index: Int) {
        val chip = reportChipStates[index]
        chip.isSelected.value = !chip.isSelected.value
    }

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