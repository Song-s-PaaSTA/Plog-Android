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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.button3Bold
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.MyReportListEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator

@Composable
fun MyReportRoute(
    navigator: ReportNavigator
) {
    MyReportScreen(
        onItemClick = { id -> navigator.navigateReportContent(id) },
        myReportViewModel = MyReportViewModel(),
        onCloseButtonClick = { navigator.navigateBack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReportScreen(
    onItemClick: (Int) -> Unit,
    myReportViewModel: MyReportViewModel,
    onCloseButtonClick: () -> Unit
) {
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
                    IconButton(onClick = {
                        onCloseButtonClick()
                    }) {
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
            LazyColumn {
                itemsIndexed(myReportViewModel.mockReports) { _, item ->
                    MyReportItem(
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
fun MyReportItem(
    data: MyReportListEntity,
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
            .padding(horizontal = 12.dp, vertical = 11.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(69.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.title,
                    style = body5Regular,
                    color = Gray600
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = data.content,
                    style = body5Regular,
                    color = Gray600
                )
            }
            Column {
                Box(
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .width(57.dp)
                        .height(27.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Green200),
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
                        .background(color = Green200),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.btn_my_report_cancel),
                        color = White,
                        style = button3Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyReportScreenPreview() {
    MyReportScreen(
        onItemClick = { },
        myReportViewModel = MyReportViewModel(),
        onCloseButtonClick = { }
    )
}