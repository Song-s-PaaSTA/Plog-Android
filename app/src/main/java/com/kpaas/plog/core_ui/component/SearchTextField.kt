package com.kpaas.plog.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import timber.log.Timber

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIconDescription: String,
    placeholderText: String,
    onClick: () -> Unit,
    enabled: Boolean
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plogging_search),
                contentDescription = leadingIconDescription
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                color = Gray400,
                style = body2Regular,
            )
        },
        textStyle = body2Regular,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gray200, RoundedCornerShape(12.dp))
            .background(White, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = TextFieldDefaults.colors(
            cursorColor = Gray400,
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            disabledContainerColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedTextColor = Gray600,
            disabledTextColor = Gray600,
            focusedTextColor = Gray600
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    )
}