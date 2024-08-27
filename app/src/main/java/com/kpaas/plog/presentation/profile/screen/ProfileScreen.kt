package com.kpaas.plog.presentation.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kpaas.plog.R
import com.kpaas.plog.core_ui.theme.Gray100
import com.kpaas.plog.presentation.profile.navigation.ProfileNavigator
import com.kpaas.plog.core_ui.theme.Gray200
import com.kpaas.plog.core_ui.theme.Gray450
import com.kpaas.plog.core_ui.theme.Gray600
import com.kpaas.plog.core_ui.theme.Green200
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.core_ui.theme.body1Medium
import com.kpaas.plog.core_ui.theme.title2Semi
import com.kpaas.plog.core_ui.theme.body1Regular
import com.kpaas.plog.core_ui.theme.body2Medium
import com.kpaas.plog.core_ui.theme.body2Regular
import com.kpaas.plog.core_ui.theme.body6Regular
import com.kpaas.plog.core_ui.theme.button2Bold

@Composable
fun ProfileRoute(
    navigator: ProfileNavigator
) {
    ProfileScreen(
        onReportClick = {}
    )
}

@Composable
fun ProfileScreen(
    onReportClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 36.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 24.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bnv_profile_off),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(66.dp)
                    .clip(CircleShape)
                    .border(1.dp, Gray100, CircleShape)
                    .background(Color.White)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Text(
                    stringResource(R.string.tv_profile_title),
                    style = body1Medium,
                    color = Gray600
                )
                Row(
                    modifier = Modifier
                        .padding(top = 11.dp)
                ) {
                    Text("줍자", style = button2Bold, color = Gray600)
                    Text(
                        stringResource(R.string.tv_profile_subtitle),
                        style = body2Medium,
                        color = Gray600
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Gray200, RoundedCornerShape(12.dp))
                .padding(top = 29.dp, start = 19.dp, bottom = 49.dp)
        ) {
            Text(
                text = stringResource(R.string.tv_profile_bookmark),
                style = title2Semi,
                color = Green200,
                modifier = Modifier.padding(bottom = 18.dp)
            )
            Text(
                text = stringResource(R.string.tv_profile_reportlist),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier
                    .clickable { onReportClick() }
                    .padding(bottom = 12.dp),
            )
            Text(
                text = stringResource(R.string.tv_profile_recentroute),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier.padding(bottom = 18.dp)
            )
            Text(
                text = stringResource(R.string.tv_profile_settings),
                style = title2Semi,
                color = Green200,
                modifier = Modifier.padding(bottom = 18.dp)
            )
            Text(
                text = stringResource(R.string.tv_provile_servcieinfo),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = stringResource(R.string.tv_profile_personalinfo),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = stringResource(R.string.tv_profile_servicecenter),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.tv_profile_versioninfo),
                    style = body2Regular,
                    color = Gray600,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Text(
                    modifier = Modifier.align(Alignment.Bottom),
                    text = stringResource(R.string.tv_profile_version),
                    style = body6Regular,
                    color = Gray450,
                )
            }
            Text(
                text = stringResource(R.string.tv_profile_logout),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Text(
                text = stringResource(R.string.tv_profile_leave),
                style = body2Regular,
                color = Gray600,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        onReportClick = {}
    )
}
