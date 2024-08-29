package com.kpaas.plog.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.title1Bold
import com.kpaas.plog.presentation.auth.screen.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(navController: NavController, modifier: Modifier = Modifier) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        delay(2500)
        when {
            (loginViewModel.getUserAccessToken().toString().isNotBlank() &&
                    loginViewModel.getCheckLogin().first()) -> {
                navController.navigate("main") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }

            else -> {
                navController.navigate("login") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White),
    ) {
        Spacer(modifier = Modifier.height(155.dp))
        Image(
            modifier = Modifier
                .size(195.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = 65.dp),
            painter = painterResource(id = R.drawable.ic_splash_logo),
            contentDescription = "Splash Logo"
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
                .padding(top = 43.dp)
                .align(Alignment.CenterHorizontally),
            text = "Plog",
            color = Green200,
            style = title1Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashScreen(navController = rememberNavController())
}