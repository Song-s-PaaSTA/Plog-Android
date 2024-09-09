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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray50
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import timber.log.Timber

@Composable
fun SearchResultScreen(
    value: String,
    onItemClick: () -> Unit,
    textField: String,
    searchViewModel: SearchViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    if (value.contains("서울")) {
        LazyColumn {
            items(5) {
                SearchResultItem(
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
                text = "등록된 결과가 없어요",
                style = title2Semi,
                color = Gray350
            )
            Text(
                text = "새로운 검색어로\n다른 지역을 찾아봐요!",
                style = body2Medium,
                color = Gray400,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SearchResultItem(
    textField: String,
    onClick: () -> Unit,
    searchViewModel: SearchViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
                Timber.d("textField: $textField")
                if (textField == "start") {
                    searchViewModel.updateStart("서울")
                    Timber.d("start: ${searchViewModel.start.value}")
                }
                else {
                    searchViewModel.updateDestination("경기")
                    Timber.d("destination: ${searchViewModel.destination.value}")
                }

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
                text = "늘푸름초등학교",
                style = body2Medium,
                color = Gray600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "서울시 노원구 덕릉로 459-21",
                style = body4Regular,
                color = Gray400,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}