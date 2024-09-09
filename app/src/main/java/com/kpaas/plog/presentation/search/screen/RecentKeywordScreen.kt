package com.kpaas.plog.presentation.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray50
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.body1Medium
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.data_local.entity.RecentKeywordEntity

@Composable
fun RecentKeywordScreen(
    onItemClick: () -> Unit,
    textField: String,
    searchViewModel: SearchViewModel
) {
    val recentKeywords = searchViewModel.recentKeywords.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(top = 15.dp, bottom = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.tv_search_recent_keyword_title),
                    style = body1Medium,
                    color = Gray600,
                )
                Text(
                    modifier = Modifier.clickable { searchViewModel.deleteAllSearchKeywords() },
                    text = stringResource(R.string.tv_search_recent_keyword_deleteAll),
                    style = body4Regular,
                    color = Gray350,
                )
            }

            LazyColumn {
                if (recentKeywords.value.isNullOrEmpty()) {
                    item {
                        Text(
                            modifier = Modifier.padding(top = 56.dp, bottom = 36.dp),
                            text = stringResource(R.string.tv_search_recent_keyword_none),
                            style = body2Medium,
                            color = Gray400,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(recentKeywords.value!!.size) { index ->
                        RecentKeywordItem(
                            data = recentKeywords.value!![index],
                            textField = textField,
                            onItemClick = { onItemClick() },
                            onDeleteClick = { searchViewModel.deleteSearchKeyword(recentKeywords.value!![index]) },
                            searchViewModel = searchViewModel
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Gray50, shape = RoundedCornerShape(4.dp))
                .padding(vertical = 18.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 9.dp)
            ) {
                Text(
                    text = stringResource(R.string.tv_search_recent_keyword_plog),
                    style = body2Medium,
                    color = Green200
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = stringResource(R.string.tv_search_recent_keyword_plog_description),
                    style = body2Medium,
                    color = Gray600
                )
            }
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.ic_search_logo),
                contentDescription = null
            )
        }
    }
}

@Composable
fun RecentKeywordItem(
    data: RecentKeywordEntity,
    textField: String,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    searchViewModel: SearchViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                onItemClick()
                when (textField) {
                    "start" -> searchViewModel.updateStart(data.keyword)
                    "destination" -> searchViewModel.updateDestination(data.keyword)
                    "reportWrite" -> searchViewModel.updateReportAddress(data.keyword)
                }
            },
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = data.keyword,
            style = body1Medium,
            color = Gray450
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_search_delete),
            contentDescription = null,
            modifier = Modifier.clickable { onDeleteClick() }
        )
    }
}