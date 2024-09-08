package com.kpaas.plog.presentation.plogging.navigation

import androidx.navigation.NavController

class PloggingNavigator(
    val navController: NavController
) {
    fun navigateCertification(start: String, destination: String, timeDifference: String) {
        val route =
            "certification?start=${start}&destination=${destination}&timeDifference=${timeDifference}"
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateSearch() {
        navController.navigate("search")
    }
}