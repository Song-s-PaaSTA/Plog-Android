package com.kpaas.plog.presentation.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.SearchTextField
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.presentation.search.navigation.SearchNavigator

@Composable
fun SearchRoute(
    navigator: SearchNavigator,
    textField: String,
    searchViewModel: SearchViewModel
) {
    SearchScreen(
        searchViewModel = searchViewModel,
        textField = textField,
        onBackClick = { navigator.navigateBack() },
        onItemClick = { navigator.navigateBack() }
    )
}

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    textField: String,
    onBackClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    var value by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { onBackClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_report_back),
                contentDescription = null
            )
            SearchTextField(
                value = value,
                onValueChange = { value = it },
                leadingIconDescription = "search",
                placeholderText = "주소를 입력하세요.",
                onClick = {  },
                enabled = true
            )
        }
        Spacer(modifier = Modifier.height(13.dp))
        if (value.isBlank()) {
            RecentKeywordScreen(searchViewModel)
        } else {
            SearchResultScreen(value, onItemClick, textField, searchViewModel)
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        searchViewModel = hiltViewModel(),
        textField = "start",
        onBackClick = {},
        onItemClick = {}
    )
}