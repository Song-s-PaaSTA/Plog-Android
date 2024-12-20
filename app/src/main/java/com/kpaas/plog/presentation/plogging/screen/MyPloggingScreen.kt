package com.kpaas.plog.presentation.plogging.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.screen.FailureScreen
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body1Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.MyPloggingListEntity
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.viewmodel.MyPloggingViewModel
import com.kpaas.plog.util.CalculateTimeDifference
import com.kpaas.plog.util.UiState

@Composable
fun MyPloggingRoute(
    ploggingNavigator: PloggingNavigator
) {
    val myPloggingViewModel: MyPloggingViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        myPloggingViewModel.getPlogging()
    }

    MyPloggingScreen(
        myPloggingViewModel = myPloggingViewModel,
        onCloseButtonClick = { ploggingNavigator.navigateBack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPloggingScreen(
    myPloggingViewModel: MyPloggingViewModel,
    onCloseButtonClick: () -> Unit,
) {
    val getPloggingState by myPloggingViewModel.getPloggingState.collectAsStateWithLifecycle(UiState.Empty)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(White)
                    .padding(vertical = 18.dp),
                title = {
                    Text(
                        text = stringResource(R.string.tv_my_plogging_appbar_title),
                        color = Gray600,
                        style = title2Semi,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onCloseButtonClick() }) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_report_back),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(White)
                .padding(horizontal = 22.dp, vertical = 8.dp)
        ) {
            when (getPloggingState) {
                UiState.Loading -> {
                    LoadingIndicator()
                }

                is UiState.Success -> {
                    val data =
                        (getPloggingState as UiState.Success<List<MyPloggingListEntity>>).data
                    if (data.isEmpty()) {
                        MyPloggingEmptyScreen()
                    } else {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(17.dp),
                            horizontalArrangement = Arrangement.spacedBy(28.dp)
                        ) {
                            itemsIndexed(data) { _, data ->
                                MyPloggingItem(data = data)
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
fun MyPloggingItem(
    data: MyPloggingListEntity
) {
    Box(
        modifier = Modifier
            .background(
                color = White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Gray200,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 13.dp, vertical = 15.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(69.dp),
                contentScale = ContentScale.FillBounds,
                model = data.ploggingImgUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 18.dp),
                text = data.startRoadAddr,
                style = body4Regular,
                color = Gray600,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                modifier = Modifier.padding(vertical = 8.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_plogging_down_arrow),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = data.endRoadAddr,
                style = body4Regular,
                color = Gray600,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.tv_my_plogging_time),
                    style = body5Regular,
                    color = Gray600
                )
                Text(
                    text = CalculateTimeDifference().formatTime(data.ploggingTime),
                    style = body5Regular,
                    color = Green200
                )
            }
        }
    }
}

@Composable
fun MyPloggingEmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ) {
        Text(
            text = stringResource(R.string.tv_empty_my_plogging),
            style = body1Regular,
            color = Gray600,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}