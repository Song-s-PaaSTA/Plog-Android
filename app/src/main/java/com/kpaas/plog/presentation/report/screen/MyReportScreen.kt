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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.screen.FailureScreen
import com.kpaas.plog.core_ui.component.dialog.PlogDialog
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body1Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.button3Bold
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.core_ui.theme.title3Semi
import com.kpaas.plog.domain.entity.MyReportListEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.viewmodel.MyReportViewModel
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.splitAddress
import timber.log.Timber

@Composable
fun MyReportRoute(
    navigator: ReportNavigator
) {
    val myReportViewModel: MyReportViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        myReportViewModel.getMyReports()
    }

    MyReportScreen(
        onItemClick = { id -> navigator.navigateReportContent(id) },
        myReportViewModel = myReportViewModel,
        onCloseButtonClick = { navigator.navigateBack() },
        onModifyButtonClick = { id -> navigator.navigateReportModify(id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReportScreen(
    onItemClick: (Long) -> Unit,
    myReportViewModel: MyReportViewModel,
    onCloseButtonClick: () -> Unit,
    onModifyButtonClick: (Long) -> Unit,
) {
    val getMyReportsState by myReportViewModel.getMyReportsState.collectAsStateWithLifecycle(UiState.Empty)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(White)
                    .padding(vertical = 15.dp),
                title = {
                    Text(
                        text = stringResource(R.string.tv_my_report_appbar_title),
                        color = Gray600,
                        style = title2Semi,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onCloseButtonClick() }
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_report_back), // 기본 백 아이콘
                            contentDescription = stringResource(R.string.tv_my_report_back_description),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(White)
                .padding(vertical = 11.dp, horizontal = 18.dp)
        ) {
            when (getMyReportsState) {
                is UiState.Loading -> {
                    LoadingIndicator()
                }

                is UiState.Success -> {
                    val data = (getMyReportsState as UiState.Success).data
                    if (data.isEmpty()) {
                        MyReportEmptyScreen()
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                        ) {
                            itemsIndexed(data) { _, item ->
                                MyReportItem(
                                    data = item,
                                    onItemClick = { onItemClick(item.reportId) },
                                    onModifyButtonClick = { onModifyButtonClick(item.reportId) },
                                    myReportViewModel = myReportViewModel
                                )
                                Spacer(modifier = Modifier.height(17.dp))
                            }
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
fun MyReportItem(
    data: MyReportListEntity,
    onItemClick: () -> Unit,
    onModifyButtonClick: () -> Unit,
    myReportViewModel: MyReportViewModel
) {
    val deleteReportState by myReportViewModel.deleteReportState.collectAsStateWithLifecycle(UiState.Empty)

    LaunchedEffect(Unit) {
        snapshotFlow { deleteReportState }
            .collect { state ->
                if (state is UiState.Success) {
                    // Handle success without triggering full recompositions
                    myReportViewModel.getMyReports()
                }
            }
    }

    var showCancelDialog by remember { mutableStateOf(false) }
    if (showCancelDialog) {
        PlogDialog(
            title = stringResource(R.string.dialog_my_report_title),
            style = title3Semi,
            onDismissText = stringResource(R.string.dialog_my_report_dismiss),
            onConfirmationText = stringResource(R.string.dialog_my_report_confirm),
            onDismissRequest = {
                showCancelDialog = false
            },
            onConfirmation = {
                showCancelDialog = false
                Timber.d("id: ${data.reportId}")
                myReportViewModel.deleteReportState(data.reportId)
            }
        )
    }
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
            .padding(horizontal = 12.dp, vertical = 11.dp)
            .clickable { onItemClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = data.reportImgUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(69.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
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
                    modifier = Modifier.padding(top = 2.dp),
                    text = second,
                    style = body5Regular,
                    color = Gray600,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column {
                Box(
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .width(57.dp)
                        .height(27.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Green200)
                        .clickable { onModifyButtonClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.btn_my_report_modify),
                        color = White,
                        style = button3Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .width(57.dp)
                        .height(27.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Green200)
                        .clickable {
                            showCancelDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.btn_my_report_cancel),
                        color = White,
                        style = button3Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun MyReportEmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "신고 내역이 없습니다.",
            style = body1Regular,
            color = Gray600
        )
    }
}