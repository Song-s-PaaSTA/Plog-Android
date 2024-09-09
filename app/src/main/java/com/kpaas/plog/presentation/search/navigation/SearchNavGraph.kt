package com.kpaas.plog.presentation.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kpaas.plog.presentation.search.screen.SearchRoute
import com.kpaas.plog.presentation.search.screen.SearchViewModel

fun NavGraphBuilder.searchNavGraph(
    searchNavigator: SearchNavigator,
    searchViewModel: SearchViewModel
) {
    composable(
        route = "search?textField={textField}",
        arguments = listOf(
            navArgument("textField") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        SearchRoute(
            navigator = searchNavigator,
            textField = backStackEntry.arguments?.getString("textField") ?: "",
            searchViewModel = searchViewModel
        )
    }
}