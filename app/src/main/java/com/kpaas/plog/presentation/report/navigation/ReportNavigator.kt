package com.kpaas.plog.presentation.report.navigation

import androidx.navigation.NavController

class ReportNavigator(
    val navController: NavController
) {
    fun navigateReport() {
        navController.navigate("report") {
            popUpTo("report") {
                inclusive = true
            }
        }
    }

    fun navigateReportWrite() {
        navController.navigate("reportWrite")
    }

    fun navigateReportContent(id: Long) {
        val route = "reportContent?id=${id}"
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateReportModify(id: Long){
        val route = "reportModify?id=${id}"
        navController.navigate(route)
    }

    fun navigateSearch(textField: String) {
        val route = "search?textField=${textField}"
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
}