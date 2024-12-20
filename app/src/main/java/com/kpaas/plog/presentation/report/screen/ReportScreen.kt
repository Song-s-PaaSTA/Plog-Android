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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.component.chip.FilterChipItem
import com.kpaas.plog.core_ui.component.chip.ReportChipItem
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.screen.FailureScreen
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Green50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body1Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.button2Bold
import com.kpaas.plog.core_ui.theme.button3Bold
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.ReportListEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.viewmodel.ReportViewModel
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.splitAddress
import kotlinx.coroutines.launch

@Composable
fun ReportRoute(
    navigator: ReportNavigator
) {
    val reportViewModel: ReportViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        reportViewModel.getReports()
    }

    ReportScreen(
        onItemClick = { id -> navigator.navigateReportContent(id) },
        onFabClick = { navigator.navigateReportWrite() },
        reportViewModel = reportViewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onItemClick: (Long) -> Unit,
    onFabClick: () -> Unit,
    reportViewModel: ReportViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showRegion by remember { mutableStateOf(true) }
    val getReportsState by reportViewModel.getReportsState.collectAsStateWithLifecycle(UiState.Empty)

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(0.6f),
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            },
            containerColor = White,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(26.dp)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 15.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .clickable {
                                showRegion = !showRegion
                            },
                        text = "지역",
                        style = button2Bold,
                        color = if (showRegion) Gray600 else Gray100
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .clickable {
                                showRegion = !showRegion
                            },
                        text = "상태",
                        style = button2Bold,
                        color = if (!showRegion) Gray600 else Gray100
                    )
                }
                if (showRegion) {
                    reportViewModel.regionChipStates.chunked(3)
                        .forEachIndexed { chunkIndex, chunk ->
                            LazyRow {
                                itemsIndexed(chunk) { index, chipState ->
                                    val realIndex = chunkIndex * 3 + index
                                    Box(modifier = Modifier.padding(end = 13.dp, bottom = 13.dp)) {
                                        ReportChipItem(
                                            chipState = chipState,
                                            onClick = {
                                                reportViewModel.regionChipSelection(
                                                    realIndex
                                                )
                                            }
                                        )
                                    }

                                }
                            }
                        }
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        itemsIndexed(reportViewModel.progressChipStates) { index, chipState ->
                            ReportChipItem(
                                chipState = chipState,
                                onClick = {
                                    reportViewModel.progressChipSelection(index)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                PlogBottomButton(text = "검색") {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                    reportViewModel.updateFilterChips()
                    reportViewModel.getReports()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            when (getReportsState) {
                is UiState.Loading -> {
                    LoadingIndicator()
                }

                is UiState.Success -> {
                    val data = (getReportsState as UiState.Success).data
                    LazyRow(
                        modifier = Modifier.padding(bottom = 23.dp)
                    ) {
                        itemsIndexed(reportViewModel.filterChipStates) { index, chipState ->
                            FilterChipItem(
                                chipState = chipState,
                                onClick = {
                                    when (index) {
                                        0 -> {
                                            showBottomSheet = true
                                            showRegion = true
                                        }

                                        1 -> {
                                            reportViewModel.filterChipSelection(1)
                                            reportViewModel.deselectChip(2)
                                            reportViewModel.getReports()
                                        }

                                        2 -> {
                                            reportViewModel.filterChipSelection(2)
                                            reportViewModel.deselectChip(1)
                                            reportViewModel.getReports()
                                        }

                                        3 -> {
                                            showBottomSheet = true
                                            showRegion = false
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(9.dp))
                        }
                    }
                    if (data.isEmpty()) {
                        ReportEmptyScreen()
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White)
                    ) {
                        itemsIndexed(data) { _, item ->
                            ReportItem(
                                data = item,
                                onClick = { onItemClick(item.reportId) }
                            )
                            Spacer(modifier = Modifier.height(17.dp))
                        }
                    }
                }

                is UiState.Failure -> {
                    FailureScreen()
                }

                else -> {}
            }
        }

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
                val (first, second) = splitAddress(data.roadAddr)
                Text(
                    text = first,
                    style = body5Regular,
                    color = Gray600,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp, bottom = 5.dp),
                    text = second,
                    style = body5Regular,
                    color = Gray600,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                when (data.reportStatus) {
                                    "청소 시작 전" -> Gray450
                                    "청소 중" -> Green50
                                    "청소 완료" -> Green200
                                    else -> Gray200
                                }
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        text = data.reportStatus,
                        style = button3Bold,
                        color = White
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Image(
                        modifier = Modifier.size(16.dp),
                        imageVector = if (data.bookmarkedByUser) ImageVector.vectorResource(id = R.drawable.ic_report_bookmark_selected)
                        else ImageVector.vectorResource(id = R.drawable.ic_report_bookmark_unselected),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier.padding(start = 3.dp),
                        text = data.bookmarkCount.toString(),
                        style = body5Regular,
                        color = Gray450
                    )
                }
            }
            AsyncImage(
                model = data.reportImgUrl,
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(69.dp)
            )
        }
    }
}

@Composable
fun ReportEmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "신고 게시물이 없습니다.",
            style = body1Regular,
            color = Gray600
        )
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    ReportScreen(
        onItemClick = { _ -> },
        onFabClick = {},
        reportViewModel = hiltViewModel()
    )
}