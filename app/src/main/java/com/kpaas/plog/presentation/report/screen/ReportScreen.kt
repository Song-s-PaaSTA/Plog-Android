package com.kpaas.plog.presentation.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Gray150
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Green50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.button2Bold
import com.kpaas.plog.core_ui.theme.button4Semi
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.ReportListEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.util.ChipState

@Composable
fun ReportRoute(
    navigator: ReportNavigator
) {
    ReportScreen(
        onItemClick = { id -> navigator.navigateReportContent(id) },
        onFabClick = { navigator.navigateReportWrite() },
        reportViewModel = ReportViewModel(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    reportViewModel: ReportViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            },
            containerColor = White,
        ) {
            Column(
                modifier = Modifier.padding(26.dp)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 15.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .clickable { },
                        text = "지역",
                        style = button2Bold,
                        color = Gray600
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .clickable { },
                        text = "상태",
                        style = button2Bold,
                        color = Gray100
                    )
                }
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    itemsIndexed(reportViewModel.reportChipStates) { index, chipState ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (chipState.isSelected.value) Green200 else Gray150
                                )
                        ) {

                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(White)
                    .padding(vertical = 15.dp),
                title = {
                    Text(
                        text = "신고 게시물",
                        color = Gray600,
                        style = title2Semi,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { onFabClick() },
                shape = CircleShape,
                containerColor = Green200,
                contentColor = White,
            ) {
                Image(
                    modifier = Modifier.padding(15.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_fab),
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(White)
                .padding(vertical = 11.dp, horizontal = 18.dp)
        ) {
            LazyRow(
                modifier = Modifier.padding(bottom = 23.dp)
            ) {
                itemsIndexed(reportViewModel.reportChipStates) { index, chipState ->
                    ReportChipItem(
                        chipState = chipState,
                        onClick = { showBottomSheet = true }
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                }
            }
            LazyColumn {
                itemsIndexed(reportViewModel.mockReports) { _, item ->
                    ReportItem(
                        data = item,
                        onClick = { onItemClick(item.id) }
                    )
                    Spacer(modifier = Modifier.height(17.dp))
                }
            }
        }

    }
}

@Composable
fun ReportChipItem(
    chipState: ChipState,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(White)
            .border(
                width = 1.dp,
                color = if (chipState.isSelected.value) Green200 else Gray150,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chipState.text,
            style = body2Regular,
            color = if (chipState.isSelected.value) Green200 else Gray600 // 선택되면 텍스트 색상도 변경
        )
        Icon(
            modifier = Modifier.padding(start = 5.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_toggle),
            contentDescription = null,
            tint = if (chipState.isSelected.value) Green200 else Gray150 // 선택된 상태에 따라 아이콘 색상 변경
        )
    }
}

@Composable
fun ReportItem(
    data: ReportListEntity,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Gray200,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 29.dp, vertical = 11.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.title,
                    style = body5Regular,
                    color = Gray600
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp, bottom = 5.dp),
                    text = data.content,
                    style = body5Regular,
                    color = Gray600
                )
                Row(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                when (data.progress) {
                                    "청소 시작 전" -> Gray450
                                    "청소 중" -> Green50
                                    "청소 완료" -> Green200
                                    else -> Gray200
                                }
                            )
                            .padding(horizontal = 10.5.dp, vertical = 5.dp),
                        text = data.progress,
                        style = button4Semi,
                        color = White
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Image(
                        modifier = Modifier.size(15.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_bookmark_unselected),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier.padding(start = 3.dp),
                        text = data.bookmarkCount.toString(),
                        style = body6Regular,
                        color = Gray450
                    )
                }
            }
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(69.dp)
            )
        }
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    ReportScreen(
        onItemClick = { _ -> },
        onFabClick = {},
        reportViewModel = ReportViewModel()
    )
}