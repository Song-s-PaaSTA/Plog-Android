package com.kpaas.plog.presentation.plogging.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kpaas.plog.presentation.plogging.screen.CertificationRoute
import com.kpaas.plog.presentation.plogging.screen.PloggingRoute

fun NavGraphBuilder.ploggingNavGraph(
    ploggingNavigator: PloggingNavigator,
) {
    composable("plogging"){
        PloggingRoute(ploggingNavigator)
    }
}

fun NavGraphBuilder.certificationNavGraph(
    ploggingNavigator: PloggingNavigator,
) {
    composable("certification") {
        CertificationRoute(ploggingNavigator)
    }
}