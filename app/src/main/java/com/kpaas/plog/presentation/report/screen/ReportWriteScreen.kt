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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.SearchTextField
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.util.toast

@Composable
fun ReportWriteRoute(
    navigator: ReportNavigator
) {
    ReportWriteScreen(
        onNextButtonClick = { navigator.navigateMain() },
        onCloseButtonClick = { navigator.navigateBack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportWriteScreen(
    onNextButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
) {
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { imageUri = it }
        }
    )

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
                placeholderText = stringResource(R.string.tv_report_write_placeholder)
            )
            Spacer(modifier = Modifier.height(27.dp))
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(134.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { galleryLauncher.launch("image/*") },
                    contentScale = ContentScale.FillBounds
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(134.dp)
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
                    if(address.isNotBlank() && imageUri != null && description.isNotBlank()) {
                        onNextButtonClick()
                        context.toast(context.getString(R.string.toast_report_write_complete))
                    }
                    else {
                        context.toast(context.getString(R.string.toast_report_write_failure))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ReportWriteScreenPreview() {
    ReportWriteScreen(
        onNextButtonClick = {},
        onCloseButtonClick = {}
    )
}