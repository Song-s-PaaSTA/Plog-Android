package com.kpaas.plog.presentation.auth.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.component.button.PlogAuthButton
import com.kpaas.plog.core_ui.theme.Gray350
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.title3Semi
import com.kpaas.plog.presentation.auth.navigation.AuthNavigator
import com.kpaas.plog.presentation.auth.viewmodel.LoginViewModel
import com.kpaas.plog.presentation.auth.viewmodel.SignupViewModel
import com.kpaas.plog.util.UiState
import com.kpaas.plog.util.toast

@Composable
fun SignupRoute(
    authNavigator: AuthNavigator,
    accessToken: String
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val signupViewModel: SignupViewModel = hiltViewModel()

    BackHandler {
        authNavigator.navigateLogin()
    }

    SignupScreen(
        signupViewModel = signupViewModel,
        onNextButtonClick = {
            loginViewModel.saveUserAccessToken(accessToken)
            loginViewModel.saveCheckLogin(true)
            authNavigator.navigateBoarding()
        },
    )
}

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel,
    onNextButtonClick: () -> Unit,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var nickname by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { imageUri = it }
        }
    )

    val patchSignUpState by signupViewModel.patchSignUpState.collectAsStateWithLifecycle(UiState.Empty)
    LaunchedEffect(patchSignUpState) {
        when(patchSignUpState) {
            is UiState.Success -> {
                onNextButtonClick()
            }
            is UiState.Failure -> {
                context.toast((patchSignUpState as UiState.Failure).msg)
            }
            else -> {}
        }
    }

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
        Box {
            IconButton(
                modifier = Modifier.size(107.dp),
                onClick = { galleryLauncher.launch("image/*") }
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(107.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_signup_profile),
                        contentDescription = null
                    )
                }
            }
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-8).dp, y = (-3).dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_signup_camera),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            placeholder = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.tv_signup_textfield_placeholder),
                        color = Gray350,
                        style = body2Regular,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            textStyle = body2Regular.copy(textAlign = TextAlign.Center),
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
            text = stringResource(R.string.btn_signup_content),
            onClick = {
                keyboardController?.hide()
                if (imageUri != null && nickname.isNotBlank()) {
                    signupViewModel.patchSignUp(
                        nickname = nickname,
                        profileImage = imageUri!!.toFile()
                    )
                } else {
                    context.toast(context.getString(R.string.tv_signup_toast))
                }
            }
        )
    }
}