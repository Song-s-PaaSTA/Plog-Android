package com.kpaas.plog.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.kpaas.plog.core_ui.theme.PlogTheme
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator
import com.kpaas.plog.presentation.main.navigation.MainNavigator
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.kpaas.plog.presentation.navigator.PlogNavHost
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            PlogTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val authNavigator = remember(navController) { AuthNavigator(navController) }
                val mainNavigator = remember(navController) { MainNavigator(navController) }
                val ploggingNavigator = remember(navController) { PloggingNavigator(navController) }
                val mapNavigator = remember(navController) { MapNavigator(navController) }
                val reportNavigator = remember(navController) { ReportNavigator(navController) }
                val rewardNavigator = remember(navController) { RewardNavigator(navController) }
                val profileNavigator = remember(navController) { ProfileNavigator(navController) }

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    content = { paddingValues ->
                        PlogNavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                            authNavigator = authNavigator,
                            mainNavigator = mainNavigator,
                            ploggingNavigator = ploggingNavigator,
                            mapNavigator = mapNavigator,
                            reportNavigator = reportNavigator,
                            rewardNavigator = rewardNavigator,
                            profileNavigator = profileNavigator
                        )
                    }
                )
            }
        }
    }
}