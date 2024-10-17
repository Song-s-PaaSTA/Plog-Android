package com.kpaas.plog.presentation.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.map.screen.MapRoute

fun NavGraphBuilder.mapNavGraph(
    mapNavigator: MapNavigator
) {
    composable("map") {
        MapRoute(mapNavigator)
    }
}