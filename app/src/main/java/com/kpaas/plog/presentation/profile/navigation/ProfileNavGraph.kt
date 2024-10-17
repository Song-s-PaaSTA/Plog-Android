package com.kpaas.plog.presentation.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.profile.screen.ProfileRoute

fun NavGraphBuilder.profileNavGraph(
    profileNavigator: ProfileNavigator,
) {
    composable("profile") {
        ProfileRoute(profileNavigator)
    }
}