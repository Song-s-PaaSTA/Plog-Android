package com.kpaas.plog.presentation.report.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kpaas.plog.presentation.report.screen.MyReportRoute
import com.kpaas.plog.presentation.report.screen.ReportContentRoute
import com.kpaas.plog.presentation.report.screen.ReportModifyRoute
import com.kpaas.plog.presentation.report.screen.ReportRoute
import com.kpaas.plog.presentation.report.screen.ReportWriteRoute
import com.kpaas.plog.presentation.search.screen.SearchViewModel

fun NavGraphBuilder.reportNavGraph(
    reportNavigator: ReportNavigator
) {
    composable("report") {
        ReportRoute(reportNavigator)
    }
}

fun NavGraphBuilder.reportWriteNavGraph(
    reportNavigator: ReportNavigator,
    searchViewModel: SearchViewModel
) {
    composable("reportWrite") {
        ReportWriteRoute(reportNavigator, searchViewModel)
    }
}

fun NavGraphBuilder.reportContentNavGraph(
    reportNavigator: ReportNavigator
) {
    composable(
        route = "reportContent?id={id}",
        arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        ReportContentRoute(
            navigator = reportNavigator,
            id = backStackEntry.arguments?.getInt("id") ?: -1
        )
    }
}

fun NavGraphBuilder.myReportNavGraph(
    reportNavigator: ReportNavigator
) {
    composable("myReport") {
        MyReportRoute(reportNavigator)
    }
}

fun NavGraphBuilder.reportModifyNavGraph(
    reportNavigator: ReportNavigator
){
    composable("reportModify") {
        ReportModifyRoute(reportNavigator)
    }
}