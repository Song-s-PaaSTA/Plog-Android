package com.kpaas.plog.core_ui.component

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem (
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
)