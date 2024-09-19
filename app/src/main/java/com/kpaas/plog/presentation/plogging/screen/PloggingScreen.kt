package com.kpaas.plog.presentation.plogging.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.kpaas.plog.presentation.plogging.viewmodel.PloggingViewModel
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
    val ploggingViewModel: PloggingViewModel = hiltViewModel()
    val buttonText by ploggingViewModel.getButtonText()
        .collectAsStateWithLifecycle(initialValue = "시작하기")
    val startTime by ploggingViewModel.getStartTime().collectAsStateWithLifecycle(initialValue = 0L)
    val start by ploggingViewModel.getStart().collectAsStateWithLifecycle(initialValue = "")
    val destination by ploggingViewModel.getDestination()
        .collectAsStateWithLifecycle(initialValue = "")
    val stopover by ploggingViewModel.getStopover().collectAsStateWithLifecycle(initialValue = "")
    val isSearchTextFieldVisible by ploggingViewModel.getSearchTextFieldVisible()
        .collectAsStateWithLifecycle(initialValue = true)
    val isStopoverTextFieldVisible by ploggingViewModel.getStopoverTextFieldVisible()
        .collectAsStateWithLifecycle(initialValue = false)

    val context = LocalContext.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    var showPloggingDialog by remember { mutableStateOf(false) }
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

    LaunchedEffect(startAddress, destinationAddress, stopoverAddress) {
        if (startAddress.isNotBlank()) {
            ploggingViewModel.saveStart(startAddress)
        }
        if (destinationAddress.isNotBlank()) {
            ploggingViewModel.saveDestination(destinationAddress)
        }
        if (stopoverAddress.isNotBlank()) {
            ploggingViewModel.saveStopover(stopoverAddress)
        }
    }

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
                onNextButtonClick(start, destination, "1분 미만")
                ploggingViewModel.clear()
                searchViewModel.apply {
                    deleteStart()
                    deleteDestination()
                    deleteStopover()
                }
            }
        )
    }

    BottomSheetScaffold(
        sheetPeekHeight = 120.dp,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
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
                                    ploggingViewModel.apply {
                                        saveAllPloggingData(
                                            buttonText = "끝내기",
                                            startTime = System.currentTimeMillis(),
                                            start = start,
                                            destination = destination,
                                            stopover = stopover,
                                            searchTextFieldVisible = false,
                                            stopoverTextFieldVisible = false
                                        )
                                    }
                                } else {
                                    context.toast(context.getString(R.string.toast_plogging_start))
                                }
                            }

                            "끝내기" -> {
                                val endTime = System.currentTimeMillis()
                                val timeDifference =
                                    CalculateTimeDifference().formatTimeDifference(endTime - startTime)

                                if (endTime - startTime < 60 * 1000) {
                                    showPloggingDialog = true
                                } else {
                                    onNextButtonClick(start, destination, timeDifference)
                                    ploggingViewModel.clear()
                                    searchViewModel.apply {
                                        deleteStart()
                                        deleteDestination()
                                        deleteStopover()
                                    }
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
                        value = startAddress,
                        onValueChange = { ploggingViewModel.saveStart(it) },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_start_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_start),
                        onClick = { onSearchClick("start") },
                        onDeleteClick = {
                            searchViewModel.deleteStart()
                            ploggingViewModel.saveStart("")
                        },
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (isStopoverTextFieldVisible) {
                        SearchTextField(
                            value = stopoverAddress,
                            onValueChange = { ploggingViewModel.saveStopover(it) },
                            leadingIconDescription = stringResource(id = R.string.img_plogging_stopover_description),
                            placeholderText = stringResource(id = R.string.tv_plogging_stopover),
                            onClick = { onSearchClick("stopover") },
                            onDeleteClick = {
                                searchViewModel.deleteStopover()
                                ploggingViewModel.saveStopover("")
                            },
                            enabled = false
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    SearchTextField(
                        value = destinationAddress,
                        onValueChange = { ploggingViewModel.saveDestination(it) },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_destination_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_destination),
                        onClick = { onSearchClick("destination") },
                        onDeleteClick = {
                            searchViewModel.deleteDestination()
                            ploggingViewModel.saveDestination("")
                        },
                        enabled = false
                    )
                    PlogStopoverButton(
                        onClick = {
                            ploggingViewModel.saveStopoverTextFieldVisible(!isStopoverTextFieldVisible)
                        },
                        text = if (!isStopoverTextFieldVisible) stringResource(id = R.string.btn_plogging_stopover_add)
                        else stringResource(id = R.string.btn_plogging_stopover_delete)
                    )
                }
            }
        }
    }
}