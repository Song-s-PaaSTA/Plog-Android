package com.kpaas.plog.presentation.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray50
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.SearchResultListEntity
import com.kpaas.plog.presentation.search.viewmodel.SearchViewModel
import com.kpaas.plog.util.UiState

@Composable
fun SearchResultScreen(
    value: String,
    onItemClick: () -> Unit,
    textField: String,
    searchViewModel: SearchViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val getPlaceState by searchViewModel.getPlaceState.collectAsStateWithLifecycle(UiState.Empty)

    LaunchedEffect(key1 = value) {
        searchViewModel.getPlace(value)
    }

    when (getPlaceState) {
        is UiState.Loading -> {
            LoadingIndicator()
        }

        is UiState.Success -> {
            val filteredResults = (getPlaceState as UiState.Success).data
            if (filteredResults.isNotEmpty()) {
                LazyColumn {
                    items(filteredResults.size) { index ->
                        SearchResultItem(
                            data = filteredResults[index],
                            textField = textField,
                            onClick = {
                                keyboardController?.hide()
                                onItemClick()
                            },
                            searchViewModel = searchViewModel
                        )
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Gray50,
                        )
                    }
                }
            } else {
                SearchResultEmptyScreen()
            }
        }

        else -> {}

    }
}

@Composable
fun SearchResultItem(
    data: SearchResultListEntity,
    textField: String,
    onClick: () -> Unit,
    searchViewModel: SearchViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
                when (textField) {
                    "start" -> searchViewModel.updateStart(
                        name = data.roadAddr,
                        longitude = data.longitude,
                        latitude = data.latitude
                    )

                    "destination" -> searchViewModel.updateDestination(
                        name = data.roadAddr,
                        longitude = data.longitude,
                        latitude = data.latitude
                    )

                    "reportWrite" -> searchViewModel.updateReportAddress(data.roadAddr)
                    "stopover" -> searchViewModel.updateStopover(
                        name = data.roadAddr,
                        longitude = data.longitude,
                        latitude = data.latitude
                    )
                }
                searchViewModel.insertSearchKeyword(
                    name = data.roadAddr,
                    longitude = data.longitude,
                    latitude = data.latitude
                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .padding(start = 13.dp)
                .padding(horizontal = 15.dp)
                .size(30.dp),
            painter = painterResource(id = R.drawable.ic_search_pin),
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(vertical = 17.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = data.placeInfo,
                style = body2Medium,
                color = Gray600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = data.roadAddr,
                style = body4Regular,
                color = Gray400,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SearchResultEmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_logo),
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.tv_search_result_empty_title),
            style = title2Semi,
            color = Gray350
        )
        Text(
            text = stringResource(R.string.tv_search_result_empty_subtitle),
            style = body2Medium,
            color = Gray400,
            textAlign = TextAlign.Center
        )
    }
}