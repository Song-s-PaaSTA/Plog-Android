package com.kpaas.plog.presentation.report.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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