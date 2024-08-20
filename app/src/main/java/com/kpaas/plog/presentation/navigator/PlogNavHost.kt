package com.kpaas.plog.presentation.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator
import com.kpaas.plog.presentation.auth.navigation.loginNavGraph
import com.kpaas.plog.presentation.main.navigation.MainNavigator
import com.kpaas.plog.presentation.main.navigation.mainNavGraph
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.kpaas.plog.presentation.map.navigation.mapNavGraph
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.navigation.ploggingNavGraph
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator
import com.kpaas.plog.presentation.profile.navigation.profileNavGraph
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.navigation.reportNavGraph
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator
import com.kpaas.plog.presentation.reward.navigation.rewardNavGraph
import com.kpaas.plog.presentation.splash.SplashScreen

@Composable
fun PlogNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authNavigator: AuthNavigator,
    mainNavigator: MainNavigator,
    ploggingNavigator: PloggingNavigator,
    mapNavigator: MapNavigator,
    reportNavigator: ReportNavigator,
    rewardNavigator: RewardNavigator,
    profileNavigator: ProfileNavigator,
    ) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash",
        ) {
            composable("splash") { SplashScreen(navController = authNavigator.navController) }
            loginNavGraph(authNavigator)
            mainNavGraph(mainNavigator, ploggingNavigator, mapNavigator, reportNavigator, rewardNavigator, profileNavigator)
            ploggingNavGraph(ploggingNavigator)
            mapNavGraph(mapNavigator)
            reportNavGraph(reportNavigator)
            rewardNavGraph(rewardNavigator)
            profileNavGraph(profileNavigator)
        }
    }
}