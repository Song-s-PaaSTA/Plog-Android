package com.kpaas.plog.util

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
)