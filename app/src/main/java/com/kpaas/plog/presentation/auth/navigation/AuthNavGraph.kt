package com.kpaas.plog.presentation.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.auth.screen.BoardingRoute
import com.kpaas.plog.presentation.auth.screen.LoginRoute
import com.kpaas.plog.presentation.auth.screen.SignupRoute

fun NavGraphBuilder.loginNavGraph(
    navigator: AuthNavigator
) {
    composable("login") {
        LoginRoute(navigator)
    }
}

fun NavGraphBuilder.signupNavGraph(
    navigator: AuthNavigator
) {
    composable("signup") {
        SignupRoute(navigator)
    }
}

fun NavGraphBuilder.boardingNavGraph(
    navigator: AuthNavigator
) {
    composable("boarding") {
        BoardingRoute(navigator)
    }
}