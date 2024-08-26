package com.kpaas.plog.presentation.report.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kpaas.plog.presentation.report.screen.ReportContentRoute
import com.kpaas.plog.presentation.report.screen.ReportRoute
import com.kpaas.plog.presentation.report.screen.ReportWriteRoute

fun NavGraphBuilder.reportNavGraph(
    reportNavigator: ReportNavigator
) {
    composable("report") {
        ReportRoute(reportNavigator)
    }
}

fun NavGraphBuilder.reportWriteNavGraph(
    reportNavigator: ReportNavigator
) {
    composable("reportWrite") {
        ReportWriteRoute(reportNavigator)
    }
}

fun NavGraphBuilder.reportContentNavGraph(
    reportNavigator: ReportNavigator
) {
    composable(
        route = "reportContent?id={id}",
        arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        ReportContentRoute(
            navigator = reportNavigator,
            id = backStackEntry.arguments?.getInt("id") ?: -1
        )
    }
}