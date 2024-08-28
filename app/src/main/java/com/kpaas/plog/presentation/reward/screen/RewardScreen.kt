package com.kpaas.plog.presentation.reward.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator

@Composable
fun RewardRoute(
    navigator: RewardNavigator
) {
    RewardScreen()
}

@Composable
fun RewardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(
                top = 31.dp,
                start = 40.dp,
                end = 40.dp
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 35.dp),
            text = "이번 달 Top3를 공개합니다!",
            color = Gray600,
            style = title2Semi,
            textAlign = TextAlign.Center
        )
        Top3Item(
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Top3Item(
                modifier = Modifier.weight(1f)
            )
            Top3Item(
                modifier = Modifier.weight(1f)
            )
        }
        LazyColumn {
            items(10) {
                RewardItem()
                Spacer(modifier = Modifier.height(13.dp))
            }
        }
    }
}

@Composable
fun Top3Item(
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Gray100,
                        shape = CircleShape
                    )
                    .size(78.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 10.dp),
                painter = painterResource(id = R.drawable.ic_reward_gold_medal),
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            Text(
                text = "환경사랑해",
                color = Gray600,
                style = body3Regular
            )
            Text(
                text = "(40점)",
                color = Gray600,
                style = body3Regular
            )
        }
    }
}

@Composable
fun RewardItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = Gray200,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(
                    color = Green200,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "8",
                color = White,
                style = body3Regular,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .border(
                    width = 1.dp,
                    color = Gray100,
                    shape = CircleShape
                ),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "환경사랑해",
            color = Gray600,
            style = body3Regular
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_reward_seed),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "40점",
            color = Gray600,
            style = body3Regular
        )
    }
}

@Preview
@Composable
fun RewardScreenPreview() {
    RewardScreen()
}