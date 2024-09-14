package com.kpaas.plog.core_ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray50
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.util.ChipState

@Composable
fun ReportChipItem(
    chipState: ChipState,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(
                if (chipState.isSelected.value) White else Gray50
            )
            .border(
                width = 1.dp,
                color = if (chipState.isSelected.value) Green200 else Gray50,
                shape = RoundedCornerShape(3.dp)
            )
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    horizontal = 15.dp,
                    vertical = 9.dp
                ),
            text = chipState.text,
            style = body2Medium,
            color = if (chipState.isSelected.value) Green200 else Gray400
        )
    }
}