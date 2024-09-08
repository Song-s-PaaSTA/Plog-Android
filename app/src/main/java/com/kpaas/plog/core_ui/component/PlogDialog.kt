package com.kpaas.plog.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kpaas.plog.core_ui.theme.Gray250
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.button3Bold
import com.kpaas.plog.core_ui.theme.title3Semi

@Composable
fun PlogDialog(
    title: String,
    style: TextStyle,
    onDismissText: String,
    onConfirmationText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(
                        top = 30.dp,
                        start = 40.dp,
                        end = 40.dp,
                        bottom = 18.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 28.dp),
                    text = title,
                    style = style,
                    color = Gray600,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 23.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onDismissRequest() },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gray250
                        ),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text(
                            text = onDismissText,
                            color = White,
                            style = button3Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onConfirmation() },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green200
                        ),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text(
                            text = onConfirmationText,
                            color = White,
                            style = button3Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlogDialogPreview() {
    PlogDialog(
        title = "다이얼로그 제목",
        style = title3Semi,
        onDismissText = "취소",
        onConfirmationText = "확인",
        onDismissRequest = {},
        onConfirmation = {}
    )
}