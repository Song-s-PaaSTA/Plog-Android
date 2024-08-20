package com.kpaas.plog.presentation.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.main.screen.MainRoute
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.kpaas.plog.presentation.map.screen.MapRoute
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.screen.PloggingRoute
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator
import com.kpaas.plog.presentation.profile.screen.ProfileRoute
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.screen.ReportRoute
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator
import com.kpaas.plog.presentation.reward.screen.RewardRoute

fun NavGraphBuilder.mainNavGraph(
    mainNavigator: MainNavigator,
    ploggingNavigator: PloggingNavigator,
    mapNavigator: MapNavigator,
    reportNavigator: ReportNavigator,
    rewardNavigator: RewardNavigator,
    profileNavigator: ProfileNavigator
) {
    composable("main") {
        MainRoute(mainNavigator)
    }
    composable("plogging") {
        PloggingRoute(ploggingNavigator)
    }
    composable("map") {
        MapRoute(mapNavigator)
    }
    composable("report") {
        ReportRoute(reportNavigator)
    }
    composable("reward") {
        RewardRoute(rewardNavigator)
    }
    composable("profile") {
        ProfileRoute(profileNavigator)
    }
}