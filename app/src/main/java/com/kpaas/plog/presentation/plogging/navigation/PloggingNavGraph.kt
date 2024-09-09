package com.kpaas.plog.presentation.plogging.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kpaas.plog.presentation.plogging.screen.CertificationRoute
import com.kpaas.plog.presentation.plogging.screen.MyPloggingRoute
import com.kpaas.plog.presentation.plogging.screen.PloggingRoute
import com.kpaas.plog.presentation.search.screen.SearchViewModel

fun NavGraphBuilder.ploggingNavGraph(
    ploggingNavigator: PloggingNavigator,
    searchViewModel: SearchViewModel
) {
    composable("plogging") {
        PloggingRoute(ploggingNavigator, searchViewModel)
    }
}

fun NavGraphBuilder.certificationNavGraph(
    ploggingNavigator: PloggingNavigator,
) {
    composable(
        route = "certification?start={start}&destination={destination}&timeDifference={timeDifference}",
        arguments = listOf(
            navArgument("start") { type = NavType.StringType },
            navArgument("destination") { type = NavType.StringType },
            navArgument("timeDifference") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        CertificationRoute(
            navigator = ploggingNavigator,
            start = backStackEntry.arguments?.getString("start") ?: "",
            destination = backStackEntry.arguments?.getString("destination") ?: "",
            timeDifference = backStackEntry.arguments?.getString("timeDifference") ?: ""
        )
    }
}

fun NavGraphBuilder.myPloggingNavGraph(
    ploggingNavigator: PloggingNavigator,
) {
    composable("myPlogging") {
        MyPloggingRoute(ploggingNavigator)
    }
}