package com.kpaas.plog.presentation.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.search.screen.SearchRoute

fun NavGraphBuilder.searchNavGraph(
    searchNavigator: SearchNavigator
) {
    composable("search") {
        SearchRoute(searchNavigator)
    }
}