package com.kpaas.plog.presentation.plogging.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.PlogDialog
import com.kpaas.plog.core_ui.component.SearchTextField
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.search.screen.SearchViewModel
import com.kpaas.plog.util.CalculateTimeDifference
import com.kpaas.plog.util.toast
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource

@Composable
fun PloggingRoute(
    navigator: PloggingNavigator,
    searchViewModel: SearchViewModel
) {
    val startAddress by searchViewModel.start.collectAsStateWithLifecycle()
    val destinationAddress by searchViewModel.destination.collectAsStateWithLifecycle()

    PloggingScreen(
        searchViewModel = searchViewModel,
        startAddress = startAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        onNextButtonClick = { start, destination, timeDifference ->
            navigator.navigateCertification(start, destination, timeDifference)
        },
        onStartClick = { textField -> navigator.navigateSearch(textField) },
        onDestinationClick = { textField -> navigator.navigateSearch(textField) }
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PloggingScreen(
    searchViewModel: SearchViewModel,
    startAddress: String,
    destinationAddress: String,
    onNextButtonClick: (String, String, String) -> Unit,
    onStartClick: (String) -> Unit,
    onDestinationClick: (String) -> Unit,
) {
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 100.0,
                minZoom = 5.0,
                locationTrackingMode = LocationTrackingMode.Follow,
            )
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }
    val context = LocalContext.current
    val sharedPreferences =
        context.getSharedPreferences("PloggingPreferences", Context.MODE_PRIVATE)
    var buttonText by remember {
        mutableStateOf(
            sharedPreferences.getString("buttonText", "시작하기") ?: "시작하기"
        )
    }
    var startTime by remember { mutableLongStateOf(sharedPreferences.getLong("startTime", 0L)) }
    var start by remember { mutableStateOf(sharedPreferences.getString("start", "") ?: "") }
    var destination by remember {
        mutableStateOf(
            sharedPreferences.getString("destination", "") ?: ""
        )
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isSearchTextFieldVisible by remember {
        mutableStateOf(
            sharedPreferences.getBoolean(
                "isSearchTextFieldVisible",
                true
            )
        )
    }

    var showPloggingDialog by remember { mutableStateOf(false) }
    if (showPloggingDialog) {
        PlogDialog(
            title = stringResource(id = R.string.dialog_plogging_title),
            style = body2Medium,
            onDismissText = stringResource(id = R.string.dialog_plogging_dismiss),
            onConfirmationText = stringResource(id = R.string.dialog_plogging_confirm),
            onDismissRequest = {
                showPloggingDialog = false
            },
            onConfirmation = {
                showPloggingDialog = false
                setPloggingPreferences(context, "시작하기", 0L, "", "", true)
                onNextButtonClick(start, destination, "1분 미만")
            }
        )
    }

    LaunchedEffect(Unit) {
        start = startAddress.ifBlank { start }
        destination = destinationAddress.ifBlank { destination }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(
                        top = 0.dp,
                        start = 35.dp,
                        bottom = 26.dp,
                        end = 35.dp
                    )
            ) {
                PlogBottomButton(
                    text = buttonText,
                    onClick = {
                        when (buttonText) {
                            "시작하기" -> {
                                if (start.isNotBlank() && destination.isNotBlank()) {
                                    startTime = System.currentTimeMillis()
                                    buttonText = "끝내기"
                                    isSearchTextFieldVisible = false

                                    setPloggingPreferences(
                                        context,
                                        buttonText,
                                        startTime,
                                        start,
                                        destination,
                                        false
                                    )
                                } else {
                                    context.toast(context.getString(R.string.toast_plogging_start))
                                }
                            }

                            "끝내기" -> {
                                searchViewModel.deleteStart()
                                searchViewModel.deleteDestination()
                                val endTime = System.currentTimeMillis()
                                val timeDifference =
                                    CalculateTimeDifference().formatTimeDifference(endTime - startTime)

                                // 1분 미만일 경우
                                if (endTime - startTime < 60 * 1000) {
                                    showPloggingDialog = true
                                } else {
                                    setPloggingPreferences(context, "시작하기", 0L, "", "", true)
                                    onNextButtonClick(start, destination, timeDifference)
                                }
                            }
                        }
                    }
                )
            }
        },
        sheetContainerColor = White,
    ) {
        Box(Modifier.fillMaxSize()) {
            NaverMap(
                locationSource = rememberFusedLocationSource(),
                properties = mapProperties,
                uiSettings = mapUiSettings
            )
            Column(
                modifier = Modifier.padding(13.dp)
            ) {
                if (isSearchTextFieldVisible) {
                    SearchTextField(
                        value = start,
                        onValueChange = { start?.let { start = it } },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_start_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_start),
                        onClick = { onStartClick("start") },
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    SearchTextField(
                        value = destination,
                        onValueChange = { destination?.let { destination = it } },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_destination_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_destination),
                        onClick = { onDestinationClick("destination") },
                        enabled = false
                    )
                }
            }
        }
    }
}

private fun setPloggingPreferences(
    context: Context,
    buttonText: String,
    startTime: Long,
    start: String,
    destination: String,
    isSearchTextFieldVisible: Boolean
) {
    val sharedPreferences =
        context.getSharedPreferences("PloggingPreferences", Context.MODE_PRIVATE)
    sharedPreferences.edit().apply {
        putString("buttonText", buttonText)
        putLong("startTime", startTime)
        putString("start", start)
        putString("destination", destination)
        putBoolean("isSearchTextFieldVisible", isSearchTextFieldVisible)
        apply()
    }
}

@Preview
@Composable
fun PloggingScreenPreview() {
    PloggingScreen(
        searchViewModel = hiltViewModel(),
        startAddress = "서울",
        destinationAddress = "경기",
        onNextButtonClick = { _, _, _ -> },
        onStartClick = {},
        onDestinationClick = {}
    )
}