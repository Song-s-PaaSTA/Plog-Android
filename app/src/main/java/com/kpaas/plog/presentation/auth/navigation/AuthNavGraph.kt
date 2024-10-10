package com.kpaas.plog.presentation.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakao.sdk.common.KakaoSdk.type
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
    composable(
        route = "signup?accessToken={accessToken}",
        arguments = listOf(
            navArgument("accessToken") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        SignupRoute(
            authNavigator = navigator,
            accessToken = backStackEntry.arguments?.getString("accessToken") ?: ""
        )
    }
}

fun NavGraphBuilder.boardingNavGraph(
    navigator: AuthNavigator
) {
    composable("boarding") {
        BoardingRoute(navigator)
    }
}