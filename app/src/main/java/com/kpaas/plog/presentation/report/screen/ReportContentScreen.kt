package com.kpaas.plog.presentation.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Green50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.button4Semi
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.ReportContentEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.viewmodel.ReportViewModel
import com.kpaas.plog.util.UiState

@Composable
fun ReportContentRoute(
    navigator: ReportNavigator,
    id: Long,
) {
    val reportViewModel: ReportViewModel = hiltViewModel()
    val getReportDetailState by reportViewModel.getReportDetailState.collectAsStateWithLifecycle(
        UiState.Empty
    )

    LaunchedEffect(true) {
        reportViewModel.getReportDetail(id)
    }

    when (getReportDetailState) {
        is UiState.Success -> {
            val data = (getReportDetailState as UiState.Success).data
            ReportContentScreen(
                data = data,
                onCloseButtonClick = { navigator.navigateBack() },
                reportViewModel = reportViewModel
            )
        }

        is UiState.Loading -> LoadingIndicator()
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportContentScreen(
    data: ReportContentEntity,
    onCloseButtonClick: () -> Unit,
    reportViewModel: ReportViewModel
) {
    var isBookmarked by remember { mutableStateOf(data.bookmarkedByUser) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(White)
                    .padding(vertical = 15.dp),
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onCloseButtonClick() },
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_appbar_close),
                        contentDescription = stringResource(R.string.tv_report_content_appbar_close),
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.tv_report_content_appbar_title),
                        color = Gray600,
                        style = title2Semi
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(White)
                .padding(
                    top = 43.dp,
                    start = 38.dp,
                    end = 38.dp
                )
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
                    .padding(horizontal = 17.dp, vertical = 12.dp)
            ) {
                Text(
                    text = data.roadAddr,
                    style = body2Regular,
                    color = Gray600,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = data.reportImgUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(27.dp))
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
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
                                else -> Gray450
                            }
                        )
                        .padding(horizontal = 10.5.dp, vertical = 6.dp),
                    text = data.reportStatus,
                    style = button4Semi,
                    color = White,
                )
                Spacer(modifier = Modifier.width(13.dp))
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            reportViewModel.postBookmark(data.reportId)
                        },
                    imageVector = if (isBookmarked) ImageVector.vectorResource(id = R.drawable.ic_report_bookmark_selected)
                    else ImageVector.vectorResource(id = R.drawable.ic_report_bookmark_unselected),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = if (isBookmarked) (data.bookmarkCount + 1).toString() else data.bookmarkCount.toString(),
                    style = body6Regular,
                    color = Gray450
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = data.createdAt,
                    color = Gray450,
                    style = body5Regular
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 30.dp, vertical = 26.dp)
            ) {
                Text(
                    text = data.reportDesc,
                    style = body4Regular,
                    color = Gray600,
                    textAlign = TextAlign.Start
                )
            }
        }

    }
}