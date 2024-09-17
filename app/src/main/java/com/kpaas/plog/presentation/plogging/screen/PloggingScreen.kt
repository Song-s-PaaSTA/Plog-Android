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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.component.button.PlogStopoverButton
import com.kpaas.plog.core_ui.component.dialog.PlogDialog
import com.kpaas.plog.core_ui.component.textfield.SearchTextField
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.search.viewmodel.SearchViewModel
import com.kpaas.plog.util.CalculateTimeDifference
import com.kpaas.plog.util.toast
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

@Composable
fun PloggingRoute(
    navigator: PloggingNavigator,
    searchViewModel: SearchViewModel
) {
    val startAddress by searchViewModel.start.collectAsStateWithLifecycle()
    val destinationAddress by searchViewModel.destination.collectAsStateWithLifecycle()
    val stopoverAddress by searchViewModel.stopoverAddress.collectAsStateWithLifecycle()

    PloggingScreen(
        searchViewModel = searchViewModel,
        startAddress = startAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        stopoverAddress = stopoverAddress ?: "",
        onNextButtonClick = { start, destination, timeDifference ->
            navigator.navigateCertification(start, destination, timeDifference)
        },
        onSearchClick = { textField -> navigator.navigateSearch(textField) },
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PloggingScreen(
    searchViewModel: SearchViewModel,
    startAddress: String,
    destinationAddress: String,
    stopoverAddress: String,
    onNextButtonClick: (String, String, String) -> Unit,
    onSearchClick: (String) -> Unit,
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
    var stopover by remember {
        mutableStateOf(
            sharedPreferences.getString("stopover", "") ?: ""
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
    var isStopoverTextFieldVisible by remember {
        mutableStateOf(
            sharedPreferences.getBoolean(
                "isStopoverTextFieldVisible",
                false
            )
        )
    }
    var isStopoverButton by rememberSaveable { mutableStateOf(true) }

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
                setPloggingPreferences(context, "시작하기", 0L, "", "", "", true, false)
                onNextButtonClick(start, destination, "1분 미만")
            }
        )
    }

    LaunchedEffect(startAddress, destinationAddress, stopoverAddress) {
        start = startAddress.ifBlank { start }
        destination = destinationAddress.ifBlank { destination }
        stopover = stopoverAddress.ifBlank { stopover }
        isStopoverTextFieldVisible =
            sharedPreferences.getBoolean("isStopoverTextFieldVisible", false)
        isStopoverButton = !isStopoverTextFieldVisible
    }

    BottomSheetScaffold(
        sheetPeekHeight = 120.dp,
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
                                    isStopoverTextFieldVisible = false
                                    setPloggingPreferences(
                                        context = context,
                                        buttonText = buttonText,
                                        startTime = startTime,
                                        start = start,
                                        destination = destination,
                                        stopover = stopover,
                                        isSearchTextFieldVisible = false,
                                        isStopoverTextFieldVisible = false
                                    )
                                } else {
                                    context.toast(context.getString(R.string.toast_plogging_start))
                                }
                            }

                            "끝내기" -> {
                                searchViewModel.deleteStart()
                                searchViewModel.deleteDestination()
                                searchViewModel.deleteStopover()
                                val endTime = System.currentTimeMillis()
                                val timeDifference =
                                    CalculateTimeDifference().formatTimeDifference(endTime - startTime)

                                // 1분 미만일 경우
                                if (endTime - startTime < 60 * 1000) {
                                    showPloggingDialog = true
                                } else {
                                    setPloggingPreferences(
                                        context = context,
                                        buttonText = "시작하기",
                                        startTime = 0L,
                                        start = "",
                                        destination = "",
                                        stopover = "",
                                        isSearchTextFieldVisible = true,
                                        isStopoverTextFieldVisible = false
                                    )
                                    isStopoverButton = true
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
                        onClick = { onSearchClick("start") },
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (isStopoverTextFieldVisible) {
                        SearchTextField(
                            value = stopover,
                            onValueChange = { stopover?.let { stopover = it } },
                            leadingIconDescription = stringResource(id = R.string.img_plogging_stopover_description),
                            placeholderText = stringResource(id = R.string.tv_plogging_stopover),
                            onClick = { onSearchClick("stopover") },
                            enabled = false
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    SearchTextField(
                        value = destination,
                        onValueChange = { destination?.let { destination = it } },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_destination_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_destination),
                        onClick = { onSearchClick("destination") },
                        enabled = false
                    )
                    PlogStopoverButton(
                        onClick = {
                            if (isStopoverButton) {
                                isStopoverButton = !isStopoverButton
                                sharedPreferences.edit().apply {
                                    putBoolean("isStopoverTextFieldVisible", true)
                                    apply()
                                }
                                isStopoverTextFieldVisible = true
                            } else {
                                isStopoverButton = !isStopoverButton
                                sharedPreferences.edit().apply {
                                    putBoolean("isStopoverTextFieldVisible", false)
                                    apply()
                                }
                                isStopoverTextFieldVisible = false
                            }
                        },
                        text = if (isStopoverButton) stringResource(id = R.string.btn_plogging_stopover_add)
                        else stringResource(id = R.string.btn_plogging_stopover_delete)
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
    stopover: String,
    isSearchTextFieldVisible: Boolean,
    isStopoverTextFieldVisible: Boolean
) {
    val sharedPreferences =
        context.getSharedPreferences("PloggingPreferences", Context.MODE_PRIVATE)
    sharedPreferences.edit().apply {
        putString("buttonText", buttonText)
        putLong("startTime", startTime)
        putString("start", start)
        putString("destination", destination)
        putString("stopover", stopover)
        putBoolean("isSearchTextFieldVisible", isSearchTextFieldVisible)
        putBoolean("isStopoverTextFieldVisible", isStopoverTextFieldVisible)
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
        stopoverAddress = "",
        onNextButtonClick = { _, _, _ -> },
        onSearchClick = {}
    )
}