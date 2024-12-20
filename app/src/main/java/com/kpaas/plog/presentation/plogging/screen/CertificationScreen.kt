package com.kpaas.plog.presentation.plogging.screen

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.title2Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.viewmodel.PloggingViewModel
import com.kpaas.plog.util.CalculateTimeDifference
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.showCustomToast
import com.kpaas.plog.util.stringOf
import com.kpaas.plog.util.toast
import com.kpaas.plog.util.uriToFile
import timber.log.Timber

@Composable
fun CertificationRoute(
    navigator: PloggingNavigator,
    start: String,
    destination: String,
    timeDifference: String,
) {
    val context = LocalContext.current
    val ploggingViewModel: PloggingViewModel = hiltViewModel()
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 3000) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            context.toast(context.getString(R.string.toast_certification_back_handler))
        }
        backPressedTime = System.currentTimeMillis()
    }

    CertificationScreen(
        ploggingViewModel = ploggingViewModel,
        start = start,
        destination = destination,
        timeDifference = timeDifference,
        onNextButtonClick = { navigator.navigateBack() }
    )
}

@Composable
fun CertificationScreen(
    ploggingViewModel: PloggingViewModel,
    start: String,
    destination: String,
    timeDifference: String,
    onNextButtonClick: () -> Unit,
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { imageUri = it }
        }
    )
    val calculatedTimeDifference = CalculateTimeDifference().formatTime(timeDifference)
    val postPloggingProof by ploggingViewModel.postPloggingProof.collectAsStateWithLifecycle(UiState.Empty)

    LaunchedEffect(postPloggingProof) {
        when (postPloggingProof) {
            is UiState.Success -> {
                Timber.d("postPloggingProof success")
                if (calculatedTimeDifference == "1분 미만") {
                    showCustomToast(
                        context,
                        context.stringOf(R.string.toast_plogging_certification_complete_without_reward)
                    )
                } else {
                    showCustomToast(
                        context,
                        context.stringOf(R.string.toast_plogging_certification_complete)
                    )
                }
                onNextButtonClick()
            }

            is UiState.Failure -> {
                Timber.e((postPloggingProof as UiState.Failure).msg)
            }

            else -> {}
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(
                top = 22.dp,
                start = 38.dp,
                bottom = 37.dp,
                end = 38.dp
            )
    ) {
        Row {
            Text(
                text = calculatedTimeDifference,
                style = title2Semi,
                color = Green200
            )
            Text(
                text = stringResource(R.string.tv_plogging_certification_title),
                style = title2Semi,
                color = Gray600
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 29.dp),
            text = stringResource(R.string.tv_plogging_certification_subtitle),
            style = title2Regular,
            color = Gray600,
        )
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = stringResource(R.string.tv_plogging_certification_start),
            style = body6Regular,
            color = Gray400
        )
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
                .padding(horizontal = 16.dp, vertical = 11.5.dp)
        ) {
            Text(
                text = start,
                style = body2Regular,
                color = Gray600,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = stringResource(R.string.tv_plogging_certification_destination),
            style = body6Regular,
            color = Gray400
        )
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
                .padding(horizontal = 16.dp, vertical = 11.5.dp)
        ) {
            Text(
                text = destination,
                style = body2Regular,
                color = Gray600,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(29.dp))
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(208.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { galleryLauncher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(208.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { galleryLauncher.launch("image/*") }
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
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
        Spacer(modifier = Modifier.weight(1f))
        PlogBottomButton(
            text = stringResource(R.string.btn_plogging_certification),
            onClick = {
                imageUri?.let {
                    val file = uriToFile(it, context)
                    ploggingViewModel.postPloggingProof(
                        startRoadAddr = start,
                        endRoadAddr = destination,
                        ploggingTime = timeDifference,
                        file = file
                    )
                } ?: context.toast(context.getString(R.string.toast_plogging_certification_failure))
            }

        )
    }
}
