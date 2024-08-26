package com.kpaas.plog.presentation.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Green50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.button4Semi
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.ReportListEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator

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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
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
            LazyColumn {
                itemsIndexed(reportViewModel.mockReports) { _, item ->
                    ReportItem(
                        data = item,
                        onClick = { onItemClick(item.id)}
                    )
                    Spacer(modifier = Modifier.height(17.dp))
                }
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
                                    "Not Started" -> Gray450
                                    "In Progress" -> Green50
                                    "Done" -> Green200
                                    else -> Gray200
                                }
                            )
                            .padding(horizontal = 10.dp, vertical = 3.dp),
                        text = data.progress,
                        style = button4Semi,
                        color = White
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_bookmark),
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