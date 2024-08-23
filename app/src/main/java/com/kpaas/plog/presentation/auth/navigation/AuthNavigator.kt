package com.kpaas.plog.presentation.auth.navigation

import androidx.navigation.NavHostController

class AuthNavigator(
    val navController: NavHostController
) {
    fun navigateLogin() {
        navController.navigate("login") {
            popUpTo("login") {
                inclusive = true
            }
        }
    }
    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateSignup() {
        navController.navigate("signup") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigateBoarding() {
        navController.navigate("boarding") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
    fun navigateMain() {
        navController.navigate("main"){
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}