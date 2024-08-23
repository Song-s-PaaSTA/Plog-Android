package com.kpaas.plog.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogAuthButton
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.title3Semi
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator

@Composable
fun SignupRoute(
    authNavigator: AuthNavigator
) {
    SignupScreen(
        onNextButtonClick = { authNavigator.navigateBoarding() },
    )
}

@Composable
fun SignupScreen(
    onNextButtonClick: () -> Unit,
) {
    var nickname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(
                top = 80.dp,
                start = 40.dp,
                bottom = 49.dp,
                end = 40.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = stringResource(R.string.tv_signup_title),
            color = Gray600,
            style = title3Semi,
            textAlign = TextAlign.Center
        )
        Box{
            IconButton(
                modifier = Modifier.size(107.dp),
                onClick = { /*TODO*/ }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_signup_profile),
                    contentDescription = null
                )
            }
            Image(
                modifier = Modifier.align(Alignment.BottomEnd),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_signup_camera),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        TextField(
            value = nickname,
            onValueChange = { /*TODO*/ },
            placeholder = {
                Text(
                    text = "닉네임",
                    color = Gray350,
                    style = body2Regular,
                    textAlign = TextAlign.Center
                )
            },
            textStyle = body2Regular,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Green200,
                focusedIndicatorColor = Green200,
                unfocusedIndicatorColor = Green200,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        PlogAuthButton(
            text = "확인",
            onClick = { onNextButtonClick() }
        )
    }
}

@Preview
@Composable
fun PreviewSignupScreen() {
    SignupScreen(
        onNextButtonClick = {}
    )
}