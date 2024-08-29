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
}