package com.kpaas.plog.core_ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray150
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.util.ChipState

@Composable
fun FilterChipItem(
    chipState: ChipState,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(White)
            .border(
                width = 1.dp,
                color = if (chipState.isSelected.value) Green200 else Gray150,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chipState.text,
            style = body2Regular,
            color = if (chipState.isSelected.value) Green200 else Gray600 // 선택되면 텍스트 색상도 변경
        )
        Icon(
            modifier = Modifier.padding(start = 5.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_toggle),
            contentDescription = null,
            tint = if (chipState.isSelected.value) Green200 else Gray150 // 선택된 상태에 따라 아이콘 색상 변경
        )
    }
}