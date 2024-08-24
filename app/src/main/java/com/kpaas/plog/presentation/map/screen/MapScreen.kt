package com.kpaas.plog.presentation.map.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.naver.maps.map.compose.ExperimentalNaverMapApi
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
    NaverMap(
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen()
}