package com.kpaas.plog.presentation.auth.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogAuthButton
import com.kpaas.plog.core_ui.theme.Gray150
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator

@Composable
fun BoardingRoute(
    authNavigator: AuthNavigator
) {
    BoardingScreen(
        onNextButtonClick = { authNavigator.navigateMain() },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoardingScreen(
    onNextButtonClick: () -> Unit,
) {
    val pages = listOf(
        BoardingPage(
            title = stringResource(R.string.tv_boarding1_title),
            imageRes = R.drawable.ic_boarding_icon1
        ),
        BoardingPage(
            title = stringResource(R.string.tv_boarding2_title),
            imageRes = R.drawable.ic_boarding_icon2
        ),
        BoardingPage(
            title = stringResource(R.string.tv_boarding3_title),
            imageRes = R.drawable.ic_boarding_icon3
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(
                top = 80.dp,
                start = 40.dp,
                bottom = 51.dp,
                end = 40.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopCenter),
                    text = pages[page].title,
                    color = Gray600,
                    style = title2Semi,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(id = pages[page].imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }

        if (pagerState.currentPage == pages.size - 1) {
            PlogAuthButton(
                text = stringResource(R.string.btn_boarding_text),
                onClick = { onNextButtonClick() }
            )
        } else {
            Row {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Green200 else Gray150
                    Box(
                        modifier = Modifier
                            .padding(7.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }
        }
    }

}

data class BoardingPage(
    val title: String,
    val imageRes: Int
)

@Preview
@Composable
fun BoardingScreenPreview() {
    BoardingScreen(onNextButtonClick = {})
}