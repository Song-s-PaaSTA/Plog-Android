package com.kpaas.plog.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class ChipStyle(
    val selectedColor: Color,
    val unselectedColor: Color,
    val chipTextStyle: TextStyle,
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val chipModifier: Modifier = Modifier,
)