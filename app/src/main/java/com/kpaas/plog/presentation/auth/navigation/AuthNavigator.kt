package com.kpaas.plog.presentation.auth.navigation

import androidx.navigation.NavHostController

class AuthNavigator(
    val navController: NavHostController
) {
    fun navigateSignup() {
        navController.navigate("signup")
    }

    fun navigateBoarding() {
        navController.navigate("boarding") {
            popUpTo("login") {
                inclusive = true
            }
        }
    }

    fun navigateMain() {
        navController.navigate("main") {
            popUpTo("boarding") {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}