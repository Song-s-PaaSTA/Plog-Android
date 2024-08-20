package com.kpaas.plog.presentation.plogging.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kpaas.plog.core_ui.theme.Gray400
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body4Regular
import com.kpaas.plog.core_ui.theme.body5Regular
import com.kpaas.plog.domain.entity.ExampleEntity

@Composable
fun ExampleItem(data: ExampleEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(vertical = 10.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column{
            Text(
                text = data.firstName,
                color = Gray600,
                style = body2Regular
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = data.email,
                color = Gray400,
                style= body4Regular
            )
        }
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = data.avatar,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun ExampleItemPreview() {
    ExampleItem(ExampleEntity("email", "firstName", "https://avatars.githubusercontent.com/u/91470334?v=4"))
}