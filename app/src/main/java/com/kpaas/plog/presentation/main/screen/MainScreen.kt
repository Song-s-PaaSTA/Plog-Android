package com.kpaas.plog.presentation.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.BottomNavigationItem
import com.kpaas.plog.core_ui.theme.Gray300
import com.kpaas.plog.core_ui.theme.Gray500
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.presentation.main.navigation.MainNavigator
import com.kpaas.plog.presentation.map.navigation.MapNavigator
import com.kpaas.plog.presentation.map.screen.MapRoute
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.screen.PloggingRoute
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator
import com.kpaas.plog.presentation.profile.screen.ProfileRoute
import com.kpaas.plog.presentation.report.navigation.ReportNavigator
import com.kpaas.plog.presentation.report.screen.ReportRoute
import com.kpaas.plog.presentation.reward.navigation.RewardNavigator
import com.kpaas.plog.presentation.reward.screen.RewardRoute

@Composable
fun MainRoute(
    mainNavigator: MainNavigator
) {
    MainScreen(
        navController = mainNavigator.navController
    )
}

@Composable
fun MainScreen(
    navController: NavHostController
) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_plogging_on),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_plogging_off),
            label = "플로깅 루트"
        ),
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_map_on),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_map_off),
            label = "쓰레기 지도"
        ),
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_report_on),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_report_off),
            label = "신고"
        ),
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_reward_on),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_reward_off),
            label = "리워드"
        ),
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_profile_on),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bnv_profile_off),
            label = "프로필"
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.background(White),
                containerColor = White
            ) {
                items.forEachIndexed { index, item ->
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = body5Regular
                                )
                            },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = Gray500,
                                unselectedTextColor = Gray300,
                                indicatorColor = Color.White
                            ),
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> PloggingRoute(navigator = PloggingNavigator(navController = navController))
                1 -> MapRoute(navigator = MapNavigator(navController = navController))
                2 -> ReportRoute(navigator = ReportNavigator(navController = navController))
                3 -> RewardRoute(navigator = RewardNavigator(navController = navController))
                4 -> ProfileRoute(navigator = ProfileNavigator(navController = navController))
            }
        }

    }
}

// 눌러질때의 ripple 제거
private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha {
        return RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}