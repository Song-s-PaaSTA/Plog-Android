package com.kpaas.plog.core_ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray50
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body1Semi

@Composable
fun PlogStopoverButton(
    onClick: () -> Unit,
    text: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 120.dp)
    ) {
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 10.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = White
            ),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Gray50),
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 8.dp),
            onClick = {
                onClick()
            }
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_plogging_star),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 7.dp),
                text = text,
                style = body1Semi,
                color = Gray600
            )
        }
    }
}
