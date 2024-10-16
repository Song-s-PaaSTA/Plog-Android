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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.textfield.SearchTextField
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.viewmodel.MyReportViewModel
import com.kpaas.plog.presentation.search.viewmodel.SearchViewModel
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.showCustomToast
import com.kpaas.plog.util.stringOf
import com.kpaas.plog.util.uriToFile
import timber.log.Timber

@Composable
fun ReportWriteRoute(
    navigator: ReportNavigator,
    searchViewModel: SearchViewModel
) {
    val reportAddress by searchViewModel.reportAddress.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    ReportWriteScreen(
        searchViewModel = searchViewModel,
        reportAddress = reportAddress ?: "",
        onNextButtonClick = { navigator.navigateReport() },
        onCloseButtonClick = {
            searchViewModel.deleteReportAddress()
            keyboardController?.hide()
            navigator.navigateBack()
        },
        onSearchClick = { textField -> navigator.navigateSearch(textField) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportWriteScreen(
    searchViewModel: SearchViewModel,
    reportAddress: String,
    onNextButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    val myReportViewModel: MyReportViewModel = hiltViewModel()
    val postReportState by myReportViewModel.postReportState.collectAsStateWithLifecycle(UiState.Empty)

    when (postReportState) {
        is UiState.Success -> {
            showCustomToast(
                context,
                context.stringOf(R.string.toast_report_write_complete)
            )
            searchViewModel.deleteReportAddress()
            onNextButtonClick()
        }

        is UiState.Loading -> LoadingIndicator()
        is UiState.Empty -> {}
        else -> {
            Timber.e("Post Report Failure: $postReportState")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { imageUri = it }
        }
    )

    LaunchedEffect(reportAddress) {
        address = reportAddress.ifBlank { address }
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
                        contentDescription = stringResource(R.string.tv_report_write_appbar_close),
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.tv_report_write_appbar_title),
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
                    top = 27.dp,
                    start = 36.dp,
                    bottom = 37.dp,
                    end = 36.dp
                )
        ) {
            SearchTextField(
                value = address,
                onValueChange = { address = it },
                leadingIconDescription = stringResource(R.string.tv_report_write_search_description),
                placeholderText = stringResource(R.string.tv_report_write_placeholder),
                onClick = { onSearchClick("reportWrite") },
                onDeleteClick = {
                    address = ""
                    searchViewModel.deleteReportAddress()
                },
                enabled = false
            )
            Spacer(modifier = Modifier.height(27.dp))
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { galleryLauncher.launch("image/*") }
                        .border(
                            width = 1.dp,
                            color = Gray200,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 13.dp),
                            text = stringResource(R.string.img_plogging_certification_placeholder),
                            style = body3Regular,
                            color = Gray600
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_plogging_camera),
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(27.dp))
            Text(
                modifier = Modifier.padding(bottom = 9.dp),
                text = stringResource(R.string.tv_report_write_description),
                style = body3Regular,
                color = Gray600
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
                text = stringResource(R.string.btn_report_write),
                onClick = {
                    if (address.isNotBlank() && imageUri != null && description.isNotBlank()) {
                        val file = uriToFile(imageUri!!, context)
                        myReportViewModel.postReport(
                            reportDesc = description,
                            roadAddr = address,
                            reportStatus = "청소 시작 전",
                            reportImgFile = file
                        )
                    } else {
                        showCustomToast(
                            context,
                            context.stringOf(R.string.toast_report_write_failure)
                        )
                    }
                }
            )
        }
    }
}