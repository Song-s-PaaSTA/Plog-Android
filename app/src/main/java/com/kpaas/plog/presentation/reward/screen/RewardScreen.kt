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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.indicator.LoadingIndicator
import com.kpaas.plog.core_ui.screen.FailureScreen
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.domain.entity.RewardListEntity
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator
import com.kpaas.plog.presentation.reward.viewmodel.RewardViewModel
import com.kpaas.plog.util.UiState

@Composable
fun RewardRoute(
    navigator: RewardNavigator
) {
    val rewardViewModel: RewardViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        rewardViewModel.getRewards()
    }

    RewardScreen(
        rewardViewModel = rewardViewModel
    )
}

@Composable
fun RewardScreen(
    rewardViewModel: RewardViewModel
) {
    val getRewardState by rewardViewModel.getRewardState.collectAsStateWithLifecycle(UiState.Empty)
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
        when (getRewardState) {
            UiState.Loading -> {
                LoadingIndicator()
            }

            is UiState.Success -> {
                val data = (getRewardState as UiState.Success).data
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 35.dp),
                    text = stringResource(R.string.tv_reward_title),
                    color = Gray600,
                    style = title2Semi,
                    textAlign = TextAlign.Center
                )
                Top3Item(
                    modifier = Modifier.fillMaxWidth(),
                    data = data[0],
                    rank = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 35.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Top3Item(
                        modifier = Modifier.weight(1f),
                        data = data[1],
                        rank = 2,
                    )
                    Top3Item(
                        modifier = Modifier.weight(1f),
                        data = data[2],
                        rank = 3
                    )
                }
                LazyColumn {
                    itemsIndexed(data) { index, item ->
                        if (index > 2) {
                            RewardItem(
                                index = index,
                                data = item
                            )
                            Spacer(modifier = Modifier.height(13.dp))
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

@Composable
fun Top3Item(
    modifier: Modifier = Modifier,
    data: RewardListEntity,
    rank: Int
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Gray100,
                        shape = CircleShape
                    )
                    .size(78.dp),
                model = data.profileImageUrl,
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 10.dp),
                painter = when (rank) {
                    1 -> painterResource(id = R.drawable.ic_reward_gold_medal)
                    2 -> painterResource(id = R.drawable.ic_reward_silver_medal)
                    else -> painterResource(id = R.drawable.ic_reward_bronze_medal)
                },
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            Text(
                text = data.nickname,
                color = Gray600,
                style = body3Regular
            )
            Text(
                text = "(${data.score}점)",
                color = Gray600,
                style = body3Regular
            )
        }
    }
}

@Composable
fun RewardItem(
    index: Int,
    data: RewardListEntity
) {
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
                text = (index + 1).toString(),
                color = White,
                style = body3Regular,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .border(
                    width = 1.dp,
                    color = Gray100,
                    shape = CircleShape
                ),
            model = data.profileImageUrl,
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = data.nickname,
            color = Gray600,
            style = body3Regular
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(24.dp),
            painter = when (data.score) {
                in 0..3 -> painterResource(id = R.drawable.ic_reward_seed)
                in 4..7 -> painterResource(id = R.drawable.ic_reward_sprout)
                in 8..11 -> painterResource(id = R.drawable.ic_reward_tree1)
                in 12..15 -> painterResource(id = R.drawable.ic_reward_tree2)
                in 16..19 -> painterResource(id = R.drawable.ic_reward_tree3)
                in 20..23 -> painterResource(id = R.drawable.ic_reward_tree4)
                in 24..27 -> painterResource(id = R.drawable.ic_reward_tree5)
                else -> painterResource(id = R.drawable.ic_reward_tree6)
            },
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "${data.score}점",
            color = Gray600,
            style = body3Regular
        )
    }
}