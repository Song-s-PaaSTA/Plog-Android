package com.kpaas.plog.presentation.plogging.navigation

import androidx.navigation.NavController

class PloggingNavigator(
    val navController: NavController
) {
    fun navigateCertification(start: String, destination: String) {
        val route = "certification?start=${start}&destination=${destination}"
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
    fun navigatePlogging() {
        navController.navigate("plogging") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}