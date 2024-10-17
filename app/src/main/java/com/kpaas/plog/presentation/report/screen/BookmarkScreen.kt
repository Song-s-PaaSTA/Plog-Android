package com.kpaas.plog.presentation.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.screen.FailureScreen
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.Green50
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body1Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.button4Semi
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.BookmarkEntity
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.viewmodel.BookmarkViewModel
import com.kpaas.plog.util.UiState

@Composable
fun BookmarkRoute(
    navigator: ReportNavigator
) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    LaunchedEffect(true) {
        bookmarkViewModel.getMyBookmarks()
    }

    BookmarkScreen(
        onItemClick = { id -> navigator.navigateReportContent(id) },
        onCloseButtonClick = { navigator.navigateBack() },
        bookmarkViewModel = bookmarkViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    onItemClick: (Long) -> Unit,
    onCloseButtonClick: () -> Unit,
    bookmarkViewModel: BookmarkViewModel
) {
    val getBookmarkState by bookmarkViewModel.getBookmarkState.collectAsStateWithLifecycle(UiState.Empty)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(White)
                    .padding(vertical = 15.dp),
                title = {
                    Text(
                        text = stringResource(R.string.tv_bookmark_title),
                        color = Gray600,
                        style = title2Semi,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onCloseButtonClick() }
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_report_back), // 기본 백 아이콘
                            contentDescription = stringResource(R.string.tv_my_report_back_description),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(White)
                .padding(vertical = 15.dp, horizontal = 18.dp)
        ) {
            when (getBookmarkState) {
                is UiState.Loading -> {
                    LoadingIndicator()
                }

                is UiState.Success -> {
                    val data = (getBookmarkState as UiState.Success).data
                    if (data.isEmpty()) {
                        EmptyBookmarkScreen()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(data) { _, item ->
                                BookmarkItem(
                                    data = item,
                                    onClick = { onItemClick(item.reportId) }
                                )
                                Spacer(modifier = Modifier.height(17.dp))
                            }
                        }
                    }
                }

                is UiState.Failure -> {
                    FailureScreen()
                }

                else -> {}
            }
        }
    }
}

@Composable
fun BookmarkItem(
    data: BookmarkEntity,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Gray200,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 30.dp, vertical = 14.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.roadAddr,
                    style = body5Regular,
                    color = Gray600
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp, bottom = 5.dp),
                    text = data.roadAddr,
                    style = body5Regular,
                    color = Gray600
                )
                Row(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                when (data.reportStatus) {
                                    "청소 시작 전" -> Gray450
                                    "청소 중" -> Green50
                                    "청소 완료" -> Green200
                                    else -> Gray200
                                }
                            )
                            .padding(horizontal = 10.5.dp, vertical = 5.dp),
                        text = data.reportStatus,
                        style = button4Semi,
                        color = White
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Image(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_bookmark_selected),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier.padding(start = 3.dp),
                        text = data.bookmarkCount.toString(),
                        style = body6Regular,
                        color = Gray450
                    )
                }
            }
            AsyncImage(
                model = data.reportImgUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(69.dp)
            )
        }
    }
}

@Composable
fun EmptyBookmarkScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.tv_bookmark_empty),
            style = body1Regular,
            color = Gray600
        )
    }
}
