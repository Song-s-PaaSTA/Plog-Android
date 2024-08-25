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
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.SearchTextField
import com.kpaas.plog.core_ui.component.button.PlogBottomButton
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.util.CalculateTimeDifference
import com.kpaas.plog.util.toast
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import timber.log.Timber

@Composable
fun PloggingRoute(
    navigator: PloggingNavigator,
) {
    PloggingScreen(
        onNextButtonClick = { start, destination, timeDifference ->
            navigator.navigateCertification(start, destination, timeDifference)
        }
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PloggingScreen(
    onNextButtonClick: (String, String, String) -> Unit,
) {
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

                                    sharedPreferences.edit().apply {
                                        putString("buttonText", buttonText)
                                        putLong("startTime", startTime)
                                        putString("start", start)
                                        putString("destination", destination)
                                        putBoolean(
                                            "isSearchTextFieldVisible",
                                            isSearchTextFieldVisible
                                        )
                                        apply()
                                    }
                                } else {
                                    context.toast(context.getString(R.string.toast_plogging_start))
                                }
                            }

                            "끝내기" -> {
                                val endTime = System.currentTimeMillis()
                                val timeDifference =
                                    CalculateTimeDifference().formatTimeDifference(endTime - startTime)

                                sharedPreferences.edit().apply {
                                    putString("buttonText", "시작하기")
                                    putLong("startTime", 0L)
                                    putString("start", "")
                                    putString("destination", "")
                                    putBoolean("isSearchTextFieldVisible", true)
                                    apply()
                                }

                                Timber.d("timeDifference: $timeDifference")
                                onNextButtonClick(start, destination, timeDifference)
                            }
                        }
                    }
                )
            }
        },
        sheetContainerColor = White,
    ) {
        Box(Modifier.fillMaxSize()) {
            NaverMap(properties = mapProperties, uiSettings = mapUiSettings)
            Column(
                modifier = Modifier.padding(13.dp)
            ) {
                if (isSearchTextFieldVisible) {
                    SearchTextField(
                        value = start,
                        onValueChange = { start = it },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_start_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_start)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    SearchTextField(
                        value = destination,
                        onValueChange = { destination = it },
                        leadingIconDescription = stringResource(id = R.string.img_plogging_destination_description),
                        placeholderText = stringResource(id = R.string.tv_plogging_destination)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PloggingScreenPreview() {
    PloggingScreen(onNextButtonClick = { _, _, _ -> })
}