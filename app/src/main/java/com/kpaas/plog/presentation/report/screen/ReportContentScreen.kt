package com.kpaas.plog.presentation.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.button4Semi
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.report.navigation.ReportNavigator

@Composable
fun ReportContentRoute(
    navigator: ReportNavigator,
    id: Int,
) {
    ReportContentScreen(
        id = id,
        onCloseButtonClick = { navigator.navigateBack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportContentScreen(
    id: Int,
    onCloseButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onCloseButtonClick() },
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_appbar_close),
                        contentDescription = "신고 내용 닫기",
                    )
                },
                title = {
                    Text(
                        text = "신고 내용",
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
                    text = "서울 노원구 동일로190길 49 지층",
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
            Spacer(modifier = Modifier.height(27.dp))
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray200)
                        .padding(horizontal = 10.5.dp, vertical = 5.dp),
                    text = "Not Started",
                    style = button4Semi,
                    color = White,
                )
                Spacer(modifier = Modifier.width(13.dp))
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_bookmark),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "12",
                    style = body6Regular,
                    color = Gray450
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = "24.08.18",
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
                    text = "사거리 부근에 쓰레기가 많이 버려져있습니다. 박스 기름통 등 종류가 다양합니다.",
                    style = body4Regular,
                    color = Gray600,
                    textAlign = TextAlign.Start
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewReportContentScreen() {
    ReportContentScreen(
        id = 0,
        onCloseButtonClick = {}
    )
}