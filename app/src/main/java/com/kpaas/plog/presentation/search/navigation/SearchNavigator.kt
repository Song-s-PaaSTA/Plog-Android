package com.kpaas.plog.presentation.search.navigation

import androidx.navigation.NavController

class SearchNavigator(
    val navController: NavController
) {
    fun navigateBack() {
        navController.popBackStack()
    }
}