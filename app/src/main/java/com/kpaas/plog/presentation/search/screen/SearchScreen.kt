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
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.presentation.search.navigation.SearchNavigator

@Composable
fun SearchRoute(
    navigator: SearchNavigator
) {
    SearchScreen(
        onBackClick = { navigator.navigateBack() },
        onItemClick = { navigator.navigateMain() }
    )
}

@Composable
fun SearchScreen(
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
            TextField(
                value = value,
                onValueChange = { value = it },
                leadingIcon = {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_plogging_search),
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(
                        text = "주소를 입력하세요.",
                        color = Gray400,
                        style = body2Regular
                    )
                },
                textStyle = body2Regular,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Gray200, RoundedCornerShape(12.dp))
                    .background(White, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    cursorColor = Gray400,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    disabledContainerColor = White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(12.dp),
            )
        }
        Spacer(modifier = Modifier.height(13.dp))
        if (value.isBlank()) {
            RecentKeywordScreen()
        } else {
            SearchResultScreen(value, onItemClick)
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        onBackClick = {},
        onItemClick = {}
    )
}