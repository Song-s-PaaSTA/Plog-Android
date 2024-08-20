package com.kpaas.plog.presentation.report.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.report.screen.ReportRoute

fun NavGraphBuilder.reportNavGraph(
    reportNavigator: ReportNavigator
) {
    composable("report") {
        ReportRoute(reportNavigator)
    }
}