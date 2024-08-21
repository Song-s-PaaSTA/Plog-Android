package com.kpaas.plog.core_ui.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.button4Semi

@Composable
fun ProgressButton(
    text: String,
    isSelected: Boolean = false,
) {
    var isSelected by remember { mutableStateOf(isSelected) }
    Button(
        onClick = {
            isSelected = !isSelected
        },
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 3.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Green200 else Gray100
        )
    ) {
        Text(
            text = text,
            color = White,
            style = button4Semi
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressButtonPreview() {
    ProgressButton(
        text = "Not Started",
        isSelected = false
    )
}