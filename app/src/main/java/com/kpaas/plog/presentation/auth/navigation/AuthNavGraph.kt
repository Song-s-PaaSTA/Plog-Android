package com.kpaas.plog.presentation.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.auth.screen.LoginRoute

fun NavGraphBuilder.loginNavGraph(
    navigator: AuthNavigator
) {
    composable("login") {
        LoginRoute(navigator)
    }
}