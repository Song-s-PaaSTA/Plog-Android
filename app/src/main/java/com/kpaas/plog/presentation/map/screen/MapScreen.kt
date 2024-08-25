package com.kpaas.plog.presentation.map.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kpaas.plog.core_ui.theme.Gray500
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.domain.entity.MarkerEntity
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource

@Composable
fun MapRoute(
    navigator: MapNavigator
) {
    MapScreen()
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen() {
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

    val markers = listOf(
        MarkerEntity(1, 37.532600, 127.5, "후암로 5가길 5", "타워빌 옆 공터"),
        MarkerEntity(2, 37.532600, 127.4, "후암로 5가길 5", "타워빌 옆 공터"),
        MarkerEntity(3, 37.532600, 127.3, "후암로 5가길 5", "타워빌 옆 공터"),
        MarkerEntity(4, 37.532600, 127.2, "후암로 5가길 5", "타워빌 옆 공터"),
        MarkerEntity(5, 37.532600, 127.1, "후암로 5가길 5", "타워빌 옆 공터"),
    )

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            locationSource = rememberFusedLocationSource(isCompassEnabled = false),
            properties = mapProperties,
            uiSettings = mapUiSettings
        ) {
            markers.forEach { markerData ->
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            markerData.latitude,
                            markerData.longitude
                        )
                    ),
                    captionText = if (selectedMarkerId == markerData.id) markerData.caption else null,
                    captionColor = Green200,
                    subCaptionText = if (selectedMarkerId == markerData.id) markerData.subCaption else null,
                    subCaptionColor = Gray500,
                    onClick = {
                        selectedMarkerId =
                            if (selectedMarkerId == markerData.id) null else markerData.id
                        true
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen()
}