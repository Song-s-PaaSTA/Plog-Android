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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Green50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.button1Bold
import com.kpaas.plog.core_ui.theme.button4Semi
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.ReportModifyEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator

@Composable
fun ReportModifyRoute(
    navigator: ReportNavigator
) {
    ReportModifyScreen(
        data = ReportModifyEntity(
            address = "서울 노원구 동일로 190길 49 지층",
            progress = "Not Started",
            description = "사거리 부근에 쓰레기가 많이 버려져있습니다. 박스 기름통 등 종류가 다양합니다."
        ),
        onCloseButtonClick = { navigator.navigateBack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportModifyScreen(
    data: ReportModifyEntity,
    onCloseButtonClick: () -> Unit,
) {
    val initialProgress = data.progress
    val selectedProgress = remember { mutableStateOf(initialProgress) }

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
                        text = stringResource(R.string.tv_report_modify_title),
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
                    top = 5.dp,
                    start = 36.dp,
                    end = 35.dp
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
                    text = data.address,
                    style = body2Regular,
                    color = Gray600,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(19.dp))
            Text(
                text = stringResource(R.string.tv_report_modify_progress),
                style = body3Regular,
                color = Gray600
            )
            Row(
                modifier = Modifier.padding(top = 9.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val progressOptions = listOf("Not Started", "In Progress", "Done")

                progressOptions.forEach { progress ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                when {
                                    progress == initialProgress -> when (progress) {
                                        "Not Started" -> Gray450
                                        "In Progress" -> Green50
                                        "Done" -> Green200
                                        else -> Gray100
                                    }

                                    progress == selectedProgress.value -> when (progress) {
                                        "Not Started" -> Gray450
                                        "In Progress" -> Green50
                                        "Done" -> Green200
                                        else -> Gray100
                                    }

                                    else -> Gray100
                                }
                            )
                            .height(14.dp)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                            .clickable { selectedProgress.value = progress },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = progress,
                            style = button4Semi,
                            color = White
                        )
                    }
                    Spacer(modifier = Modifier.width(11.dp))
                }
            }
            Text(
                text = stringResource(R.string.tv_report_modify_detail),
                style = body3Regular,
                color = Gray600,
                modifier = Modifier.padding(bottom = 9.dp)
            )
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
                    .padding(horizontal = 30.dp, vertical = 26.dp),

                ) {
                Text(
                    text = data.description,
                    style = body4Regular,
                    color = Gray600,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = Green200,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.btn_report_modfify),
                    style = button1Bold,
                    color = White
                )
            }
        }
    }
}

@Preview
@Composable
fun ReportModifyScreenPreview() {
    ReportModifyScreen(
        data = ReportModifyEntity(
            address = "서울 노원구 동일로 190길 49 지층",
            progress = "Done",
            description = "사거리 부근에 쓰레기가 많이 버려져있습니다. 박스 기름통 등 종류가 다양합니다."
        ),
        onCloseButtonClick = {}
    )
}
