package com.kpaas.plog.presentation.profile.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator

@Composable
fun ProfileRoute(
    navigator: ProfileNavigator
) {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}