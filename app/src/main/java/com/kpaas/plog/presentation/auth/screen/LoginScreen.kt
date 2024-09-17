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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body3Regular
import com.kpaas.plog.core_ui.theme.title1Bold
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator
import com.kpaas.plog.presentation.auth.viewmodel.LoginViewModel
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.toast
import kotlinx.coroutines.flow.first
import timber.log.Timber

@Composable
fun LoginRoute(
    authNavigator: AuthNavigator
) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is UiState.Success -> {
                val accessToken = (loginState as UiState.Success).data[0]
                val refreshToken = (loginState as UiState.Success).data[1]

                if(refreshToken == loginViewModel.getUserRefreshToken().first()) {
                    loginViewModel.saveCheckLogin(true)
                    authNavigator.navigateMain()
                } else {
                    authNavigator.navigateSignup(accessToken, refreshToken)
                }
            }

            is UiState.Failure -> {
                Timber.e("Login failed: $loginState")
                context.toast((loginState as UiState.Failure).msg)
            }

            else -> {}
        }
    }

    LoginScreen(
        onKakaoButtonClick = { loginViewModel.signInWithKakao(context) },
        onNaverButtonClick = { loginViewModel.signInWithNaver(context) },
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
            text = stringResource(R.string.tv_login_subtitle),
            color = Gray600,
            style = title2Semi
        )
        Text(
            modifier = Modifier.padding(
                start = 53.dp,
                bottom = 30.dp
            ),
            text = stringResource(R.string.tv_login_title),
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
            text = stringResource(R.string.tv_login_signup_notify),
            color = Gray350,
            style = body3Regular,
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            IconButton(
                modifier = Modifier.size(60.dp),
                onClick = { onKakaoButtonClick() }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_login_kakao),
                    contentDescription = stringResource(R.string.tv_login_kakao_description),
                )
            }
            Spacer(
                modifier = Modifier.width(20.dp)
            )
            IconButton(
                modifier = Modifier.size(60.dp),
                onClick = { onNaverButtonClick() }
            ) {
                Image(
                    modifier = Modifier
                        .size(60.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_login_naver),
                    contentDescription = stringResource(R.string.tv_login_naver_description),
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