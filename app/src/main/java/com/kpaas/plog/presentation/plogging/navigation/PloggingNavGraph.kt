package com.kpaas.plog.presentation.plogging.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kpaas.plog.presentation.plogging.screen.CertificationRoute
import com.kpaas.plog.presentation.plogging.screen.PloggingRoute

fun NavGraphBuilder.ploggingNavGraph(
    ploggingNavigator: PloggingNavigator,
) {
    composable("plogging"){
        PloggingRoute(ploggingNavigator)
    }
}

fun NavGraphBuilder.certificationNavGraph(
    ploggingNavigator: PloggingNavigator,
) {
    composable(
        route = "certification?start={start}&destination={destination}",
        arguments = listOf(
            navArgument("start") { type = NavType.StringType },
            navArgument("destination") { type = NavType.StringType },
        )) { backStackEntry ->
        CertificationRoute(
            navigator = ploggingNavigator,
            start = backStackEntry.arguments?.getString("start") ?: "",
            destination = backStackEntry.arguments?.getString("destination") ?: ""
        )
    }
}