package com.kpaas.plog.presentation.report.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.ReportContentEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.viewmodel.MyReportViewModel
import com.kpaas.plog.presentation.report.viewmodel.ReportViewModel
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.uriToFile
import timber.log.Timber

@Composable
fun ReportModifyRoute(
    navigator: ReportNavigator,
    id: Long
) {
    val reportViewModel: ReportViewModel = hiltViewModel()
    val reportDetailState by reportViewModel.getReportDetailState.collectAsStateWithLifecycle(
        UiState.Empty
    )

    LaunchedEffect(true) {
        reportViewModel.getReportDetail(id)
    }

    when (reportDetailState) {
        is UiState.Success -> {
            val data = (reportDetailState as UiState.Success).data
            ReportModifyScreen(
                data = data,
                onCloseButtonClick = { navigator.navigateBack() },
                onModifyButtonClick = { navigator.navigateBack() }
            )
        }

        is UiState.Loading -> LoadingIndicator()
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportModifyScreen(
    data: ReportContentEntity,
    onCloseButtonClick: () -> Unit,
    onModifyButtonClick: () -> Unit
) {
    var selectedProgress by remember { mutableIntStateOf(0) }
    var description by remember { mutableStateOf(data.reportDesc) }
    val myReportViewModel: MyReportViewModel = hiltViewModel()
    val patchReportState by myReportViewModel.patchReportState.collectAsStateWithLifecycle(UiState.Empty)
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { imageUri = it }
        }
    )
    val context  = LocalContext.current

    when (patchReportState) {
        is UiState.Success -> {
            onModifyButtonClick()
        }

        is UiState.Loading -> {
            LoadingIndicator()
        }

        else -> { Timber.e("patchReportState is not success") }
    }

    LaunchedEffect(data.reportStatus) {
        selectedProgress = when (data.reportStatus) {
            "청소 시작 전" -> 0
            "청소 중" -> 1
            "청소 완료" -> 2
            else -> 0 // 기본값 설정
        }
    }

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
                    end = 35.dp,
                    bottom = 37.dp
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
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { galleryLauncher.launch("image/*") },
                    contentScale = ContentScale.FillBounds
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { galleryLauncher.launch("image/*") },
                    model = data.reportImgUrl,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
            Text(
                modifier = Modifier.padding(bottom = 9.dp),
                text = stringResource(R.string.tv_report_modify_progress),
                style = body3Regular,
                color = Gray600
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                items(3) { index ->
                    Button(
                        onClick = { selectedProgress = index },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedProgress == index) Green200 else Gray100
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 10.dp,
                            vertical = 3.dp
                        )
                    ) {
                        Text(
                            text = when (index) {
                                0 -> "청소 시작 전"
                                1 -> "청소 중"
                                2 -> "청소 완료"
                                else -> ""
                            },
                            color = White,
                            style = body6Regular
                        )
                    }
                }
            }
            Text(
                text = stringResource(R.string.tv_report_modify_detail),
                style = body3Regular,
                color = Gray600,
                modifier = Modifier.padding(top = 19.dp, bottom = 9.dp)
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                textStyle = body4Regular,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 30.dp, vertical = 26.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Gray400,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            PlogBottomButton(
                text = stringResource(id = R.string.btn_report_modfify),
                onClick = {
                    val file = imageUri?.let { uriToFile(it, context) }
                    myReportViewModel.patchReport(
                        reportId = data.reportId,
                        reportStatus = when (selectedProgress) {
                            0 -> "청소 시작 전"
                            1 -> "청소 중"
                            2 -> "청소 완료"
                            else -> "청소 시작 전" // 기본값 설정
                        },
                        reportDesc = description,
                        existingImgUrl = data.reportImgUrl,
                        reportImgFile = file
                    )
                },
            )
        }
    }
}
