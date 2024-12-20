package com.kpaas.plog.presentation.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.PlogTheme
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator
import com.kpaas.plog.presentation.main.navigation.MainNavigator
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.kpaas.plog.presentation.navigator.PlogNavHost
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator
import com.kpaas.plog.presentation.search.navigation.SearchNavigator
import com.kpaas.plog.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            PlogTheme {
                val context = LocalContext.current
                var backPressedState by remember { mutableStateOf(true) }
                var backPressedTime = 0L

                BackHandler(enabled = backPressedState) {
                    if (System.currentTimeMillis() - backPressedTime <= 3000) {
                        (context as Activity).finish()
                    } else {
                        backPressedState = true
                        context.toast(getString(R.string.toast_back_handler))
                    }
                    backPressedTime = System.currentTimeMillis()
                }

                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val authNavigator = remember(navController) { AuthNavigator(navController) }
                val mainNavigator = remember(navController) { MainNavigator(navController) }
                val ploggingNavigator = remember(navController) { PloggingNavigator(navController) }
                val mapNavigator = remember(navController) { MapNavigator(navController) }
                val reportNavigator = remember(navController) { ReportNavigator(navController) }
                val rewardNavigator = remember(navController) { RewardNavigator(navController) }
                val profileNavigator = remember(navController) { ProfileNavigator(navController) }
                val searchNavigator = remember(navController) { SearchNavigator(navController) }

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
                            profileNavigator = profileNavigator,
                            searchNavigator = searchNavigator,
                        )
                    }
                )
            }
        }
    }
}