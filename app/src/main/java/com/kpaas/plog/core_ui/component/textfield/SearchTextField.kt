package com.kpaas.plog.core_ui.component.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
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
    onDeleteClick: () -> Unit,
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
        trailingIcon = {
            Image(
                modifier = Modifier.clickable { onDeleteClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search_delete),
                contentDescription = null
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

@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        value = "검색어",
        onValueChange = {},
        leadingIconDescription = "검색",
        placeholderText = "검색어를 입력해주세요",
        onClick = {},
        onDeleteClick = {},
        enabled = true
    )
}