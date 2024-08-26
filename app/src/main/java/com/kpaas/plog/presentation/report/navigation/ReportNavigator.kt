package com.kpaas.plog.presentation.report.navigation

import androidx.navigation.NavController

class ReportNavigator(
    val navController: NavController
) {
    fun navigateReportWrite() {
        navController.navigate("reportWrite") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    fun navigateMain() {
        navController.navigate("main") {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}