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
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Red50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.viewmodel.PloggingViewModel
import com.kpaas.plog.presentation.search.viewmodel.SearchViewModel
import com.kpaas.plog.util.CalculateTimeDifference
import com.kpaas.plog.util.Location
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage
import timber.log.Timber

@Composable
fun PloggingRoute(
    navigator: PloggingNavigator,
    searchViewModel: SearchViewModel
) {
    val startAddress by searchViewModel.start.collectAsStateWithLifecycle(null)
    val destinationAddress by searchViewModel.destination.collectAsStateWithLifecycle(null)
    val stopoverAddress by searchViewModel.stopoverAddress.collectAsStateWithLifecycle(null)

    PloggingScreen(
        searchViewModel = searchViewModel,
        startAddress = startAddress,
        destinationAddress = destinationAddress,
        stopoverAddress = stopoverAddress,
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
    startAddress: Location?,
    destinationAddress: Location?,
    stopoverAddress: Location?,
    onNextButtonClick: (String, String, String) -> Unit,
    onSearchClick: (String) -> Unit,
) {
    val ploggingViewModel: PloggingViewModel = hiltViewModel()
    val buttonText by ploggingViewModel.getButtonText()
        .collectAsStateWithLifecycle(initialValue = "루트 추천받기")
    val startTime by ploggingViewModel.getStartTime().collectAsStateWithLifecycle(initialValue = 0L)
    val start by ploggingViewModel.getStart().collectAsStateWithLifecycle(null)
    val destination by ploggingViewModel.getDestination().collectAsStateWithLifecycle(null)
    val stopover by ploggingViewModel.getStopover().collectAsStateWithLifecycle(null)
    val isSearchTextFieldVisible by ploggingViewModel.getSearchTextFieldVisible()
        .collectAsStateWithLifecycle(true)
    val isStopoverTextFieldVisible by ploggingViewModel.getStopoverTextFieldVisible()
        .collectAsStateWithLifecycle(false)
    val route by ploggingViewModel.getRoute()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val context = LocalContext.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    var showPloggingDialog by remember { mutableStateOf<String?>(null) }
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
    val cameraPositionState = rememberCameraPositionState()
    val postPloggingRouteState by searchViewModel.postPloggingRouteState.collectAsStateWithLifecycle(
        UiState.Empty
    )

    LaunchedEffect(Unit) {
        cameraPositionState.position = CameraPosition(
            LatLng(37.5666103, 126.9783882),
            16.0
        )
    }

    LaunchedEffect(startAddress, destinationAddress, stopoverAddress) {
        if (startAddress != null) {
            ploggingViewModel.saveStart(startAddress)
        }
        if (destinationAddress != null) {
            ploggingViewModel.saveDestination(destinationAddress)
        }
        if (stopoverAddress != null) {
            ploggingViewModel.saveStopover(stopoverAddress)
        }
    }

    if (showPloggingDialog != null) {
        PlogDialog(
            title = stringResource(id = R.string.dialog_plogging_title),
            style = body2Medium,
            onDismissText = stringResource(id = R.string.dialog_plogging_dismiss),
            onConfirmationText = stringResource(id = R.string.dialog_plogging_confirm),
            onDismissRequest = {
                showPloggingDialog = null
            },
            onConfirmation = {
                showPloggingDialog?.let {
                    onNextButtonClick(start!!.name, destination!!.name, it)
                }
                showPloggingDialog = null
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
                            "경로 추천받기" -> {
                                if (start != null && destination != null) {
                                    ploggingViewModel.apply {
                                        saveAllPloggingData(
                                            buttonText = "시작하기",
                                            startTime = 0L,
                                            start = start,
                                            destination = destination,
                                            stopover = stopover,
                                            searchTextFieldVisible = false,
                                            stopoverTextFieldVisible = false
                                        )
                                    }
                                    searchViewModel.postPloggingRoute()
                                } else context.toast(context.getString(R.string.toast_plogging_start))
                            }

                            "시작하기" -> {
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
                            }

                            "끝내기" -> {
                                val endTime = System.currentTimeMillis()
                                val timeDifference =
                                    CalculateTimeDifference().formatTimeDifference(endTime - startTime)

                                if (endTime - startTime < 60 * 1000) {
                                    showPloggingDialog = timeDifference
                                } else {
                                    onNextButtonClick(
                                        start!!.name,
                                        destination!!.name,
                                        timeDifference
                                    )
                                    ploggingViewModel.clear()
                                    searchViewModel.apply {
                                        deleteStart()
                                        deleteDestination()
                                        deleteStopover()
                                    }
                                }
                            }

                            else -> Timber.e("Unknown button text: $buttonText")
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
                uiSettings = mapUiSettings,
                cameraPositionState = cameraPositionState,
            ) {
                if (start != null) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                start!!.latitude,
                                start!!.longitude
                            )
                        ),
                        icon = OverlayImage.fromResource(R.drawable.ic_map_marker),
                        iconTintColor = Red50
                    )
                    cameraPositionState.position = CameraPosition(
                        LatLng(start!!.latitude, start!!.longitude),
                        16.0
                    )
                }
                if (destination != null) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                destination!!.latitude,
                                destination!!.longitude
                            )
                        ),
                        icon = OverlayImage.fromResource(R.drawable.ic_map_marker),
                        iconTintColor = Red50
                    )
                    cameraPositionState.position = CameraPosition(
                        LatLng(destination!!.latitude, destination!!.longitude),
                        16.0
                    )
                }
                if (stopover != null) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                stopover!!.latitude,
                                stopover!!.longitude
                            )
                        ),
                        icon = OverlayImage.fromResource(R.drawable.ic_map_marker),
                        iconTintColor = Red50
                    )
                    cameraPositionState.position = CameraPosition(
                        LatLng(stopover!!.latitude, stopover!!.longitude),
                        16.0
                    )
                }
                if (buttonText != "경로 추천받기") {
                    if (route.isNotEmpty()) {
                        val coords = route.map { LatLng(it.longitude, it.latitude) }
                        PathOverlay(
                            coords = coords,
                            color = Green200,
                            outlineWidth = 2.dp,
                            outlineColor = White,
                            width = 6.dp
                        )
                        cameraPositionState.move(CameraUpdate.zoomOut())
                    } else {
                        Timber.e("Route is empty")
                    }
                    when (postPloggingRouteState) {
                        is UiState.Success -> {
                            val route =
                                (postPloggingRouteState as UiState.Success<List<LatLngEntity>>).data
                            ploggingViewModel.saveRoute(route)
                            val coords = route.map { LatLng(it.longitude, it.latitude) }
                            if (route.isNotEmpty()) {
                                PathOverlay(
                                    coords = coords,
                                    color = Green200,
                                    outlineWidth = 2.dp,
                                    outlineColor = White,
                                    width = 6.dp
                                )
                                cameraPositionState.move(CameraUpdate.zoomOut())
                            } else {
                                Timber.e("Route is empty")
                            }
                        }

                        else -> Timber.e("Unknown state: $postPloggingRouteState")
                    }
                }
            }
            Column(
                modifier = Modifier.padding(13.dp)
            ) {
                if (isSearchTextFieldVisible) {
                    SearchTextField(
                        value = startAddress?.name ?: "",
                        onValueChange = { ploggingViewModel.saveStart(startAddress) },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_start_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_start),
                        onClick = { onSearchClick("start") },
                        onDeleteClick = {
                            searchViewModel.deleteStart()
                            ploggingViewModel.saveStart(null)
                        },
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (isStopoverTextFieldVisible) {
                        SearchTextField(
                            value = stopoverAddress?.name ?: "",
                            onValueChange = { ploggingViewModel.saveStopover(stopoverAddress) },
                            leadingIconDescription = stringResource(id = R.string.img_plogging_stopover_description),
                            placeholderText = stringResource(id = R.string.tv_plogging_stopover),
                            onClick = { onSearchClick("stopover") },
                            onDeleteClick = {
                                searchViewModel.deleteStopover()
                                ploggingViewModel.saveStopover(null)
                            },
                            enabled = false
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    SearchTextField(
                        value = destinationAddress?.name ?: "",
                        onValueChange = { ploggingViewModel.saveDestination(destinationAddress) },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_destination_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_destination),
                        onClick = { onSearchClick("destination") },
                        onDeleteClick = {
                            searchViewModel.deleteDestination()
                            ploggingViewModel.saveDestination(null)
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
                if (!isSearchTextFieldVisible && buttonText == "시작하기") {
                    PlogStopoverButton(
                        onClick = {
                            ploggingViewModel.clear()
                            searchViewModel.apply {
                                deleteStart()
                                deleteDestination()
                                deleteStopover()
                            }
                        },
                        text = stringResource(id = R.string.btn_plogging_route_cancel)
                    )
                }
            }
        }
    }
}