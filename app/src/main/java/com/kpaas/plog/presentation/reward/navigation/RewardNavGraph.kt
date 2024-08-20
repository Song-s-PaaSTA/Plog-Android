package com.kpaas.plog.presentation.reward.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.reward.screen.RewardRoute

fun NavGraphBuilder.rewardNavGraph(
    rewardNavigator: RewardNavigator
) {
    composable("reward") {
        RewardRoute(rewardNavigator)
    }
}