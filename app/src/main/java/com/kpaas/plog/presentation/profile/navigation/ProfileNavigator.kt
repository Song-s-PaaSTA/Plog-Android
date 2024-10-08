package com.kpaas.plog.presentation.profile.navigation

import androidx.navigation.NavController

class ProfileNavigator(
    val navController: NavController
) {
    fun navigateLogin() {
        navController.navigate("login") {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    fun navigateMyReport() {
        navController.navigate("myReport")
    }

    fun navigateMyPlogging() {
        navController.navigate("myPlogging")
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateBookmark() {
        navController.navigate("bookmark")
    }
}