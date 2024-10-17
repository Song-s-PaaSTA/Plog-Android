package com.kpaas.plog.util

import androidx.compose.runtime.MutableState

data class ChipState(
    var text: String,
    val isSelected: MutableState<Boolean>
)