package com.kpaas.plog.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.title1Bold
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator

@Composable
fun LoginRoute(
    authNavigator: AuthNavigator
) {
    LoginScreen(
        onKakaoButtonClick = { authNavigator.navigateMain() },
        onNaverButtonClick = {},
    )
}

@Composable
fun LoginScreen(
    onKakaoButtonClick: () -> Unit,
    onNaverButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(
                top = 53.dp,
                start = 53.dp,
                bottom = 13.dp
            ),
            text = "플로깅의 기록을 남기다",
            color = Gray600,
            style = title2Semi
        )
        Text(
            modifier = Modifier.padding(
                start = 53.dp,
                bottom = 30.dp
            ),
            text = "플로그 (Plog)",
            color = Green200,
            style = title1Bold
        )
        Image(
            modifier = Modifier
                .size(195.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = 65.dp),
            painter = painterResource(id = R.drawable.ic_splash_logo),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .height(3.dp)
                .width(288.dp)
                .background(
                    color = Green200,
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 90.dp,
                    bottom = 18.dp
                )
                .align(Alignment.CenterHorizontally),
            text = "SNS 계정으로 간편 가입하기",
            color = Gray350,
            style = body3Regular,
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            IconButton(onClick = { onKakaoButtonClick() }) {
                Icon(
                    modifier = Modifier
                        .size(60.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_login_kakao),
                    contentDescription = "kakao login button",
                    tint = Color.Unspecified
                )
            }
            Spacer(
                modifier = Modifier.width(20.dp)
            )
            IconButton(onClick = { onNaverButtonClick() }) {
                Icon(
                    modifier = Modifier
                        .size(60.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_login_naver),
                    contentDescription = "naver login button",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        onKakaoButtonClick = {},
        onNaverButtonClick = {},
    )
}