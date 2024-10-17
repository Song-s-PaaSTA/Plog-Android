package com.kpaas.plog.presentation.map.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kpaas.plog.core_ui.theme.Gray500
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.domain.entity.MarkerEntity
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.kpaas.plog.util.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import timber.log.Timber

@Composable
fun MapRoute(
    navigator: MapNavigator
) {
    val mapViewModel: MapViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        mapViewModel.getTrash()
    }

    MapScreen(mapViewModel = mapViewModel)
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel
) {
    val cameraPositionState = rememberCameraPositionState()
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
            MapUiSettings(isLocationButtonEnabled = true)
        )
    }
    var selectedMarkerId by remember { mutableStateOf<Int?>(null) }
    val getTrashState by mapViewModel.getTrashState.collectAsStateWithLifecycle(UiState.Empty)

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            locationSource = rememberFusedLocationSource(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
        ) {
            when (getTrashState) {
                is UiState.Success -> {
                    val markers = (getTrashState as UiState.Success<List<MarkerEntity>>).data
                    LaunchedEffect(markers) {
                        if (markers.isNotEmpty()) {
                            cameraPositionState.position = CameraPosition(
                                LatLng(markers.first().latitude, markers.first().longitude),
                                6.0 // 초기 줌 레벨 설정
                            )
                        }
                    }

                    markers.forEach { markerData ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    markerData.latitude,
                                    markerData.longitude
                                )
                            ),
                            captionText = if (selectedMarkerId == markerData.placeId) markerData.roadAddr else null,
                            captionColor = Green200,
                            subCaptionText = if (selectedMarkerId == markerData.placeId) markerData.placeInfo else null,
                            subCaptionColor = Gray500,
                            onClick = {
                                selectedMarkerId =
                                    if (selectedMarkerId == markerData.placeId) null else markerData.placeId
                                cameraPositionState.position = CameraPosition(
                                    LatLng(markerData.latitude, markerData.longitude),
                                    16.0 // 원하는 줌 레벨 설정
                                )
                                true
                            }
                        )
                    }
                }

                is UiState.Failure -> {
                    Timber.e((getTrashState as UiState.Failure).msg)
                }

                else -> {
                    Timber.d("Unknown state")
                }
            }
        }
    }
}