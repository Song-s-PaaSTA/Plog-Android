package com.kpaas.plog.presentation.map.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap

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
            MapProperties(maxZoom = 100.0, minZoom = 5.0)
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }
    Box(Modifier.fillMaxSize()) {
        NaverMap(properties = mapProperties, uiSettings = mapUiSettings) {
            Marker(
                state = MarkerState(position = LatLng(37.532600, 127.024612)),
                captionText = "Marker in Seoul"
            )
        }
        Column {
            Button(onClick = {
                mapProperties = mapProperties.copy(
                    isBuildingLayerGroupEnabled = !mapProperties.isBuildingLayerGroupEnabled
                )
            }) {
                Text(text = "Toggle isBuildingLayerGroupEnabled")
            }
            Button(onClick = {
                mapUiSettings = mapUiSettings.copy(
                    isLocationButtonEnabled = !mapUiSettings.isLocationButtonEnabled
                )
            }) {
                Text(text = "Toggle isLocationButtonEnabled")
            }
        }
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen()
}