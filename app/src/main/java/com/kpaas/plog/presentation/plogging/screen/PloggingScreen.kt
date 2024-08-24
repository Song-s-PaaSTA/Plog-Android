package com.kpaas.plog.presentation.plogging.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.SearchTextField
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap

@Composable
fun PloggingRoute(
    navigator: PloggingNavigator,
) {
    PloggingScreen()
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun PloggingScreen() {
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
    var start by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        NaverMap(properties = mapProperties, uiSettings = mapUiSettings)
        Column(
            modifier = Modifier.padding(13.dp)
        ) {
            SearchTextField(
                value = start,
                onValueChange = { start = it},
                leadingIconDescription = R.string.img_plogging_start_description,
                placeholderText = R.string.tv_plogging_start
            )
            Spacer(modifier = Modifier.height(5.dp))
            SearchTextField(
                value = destination,
                onValueChange = { destination = it},
                leadingIconDescription = R.string.img_plogging_destination_description,
                placeholderText = R.string.tv_plogging_destination
            )
        }
    }
}

@Preview
@Composable
fun PloggingScreenPreview() {
    PloggingScreen()
}