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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
import com.kpaas.plog.core_ui.theme.body2Regular

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
            .background(White)
            .padding(20.dp)
    ) {
        Row {
            Spacer(Modifier.width(20.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bnv_profile_off),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(66.dp)
                    .clip(CircleShape)
                    .border(1.dp, Gray100, CircleShape)
                    .background(Color.White),
            )
            Spacer(Modifier.width(15.dp))
            Column {
                Spacer(Modifier.size(5.dp))
                Text("안녕하세요,", style = body1Medium, color = Gray600)
                Spacer(Modifier.size(11.dp))
                Row {
                    Text("줍자", style = title2Semi, color = Gray600)
                    Text("님", style = title2Semi, color = Gray600)
                }
            }
        }
        Spacer(Modifier.height(17.dp))
        Column(
            modifier = Modifier
                .width(315.dp)
                .height(382.dp)
                .border(2.dp, Gray200, RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Text("즐겨찾기", style = title2Semi, color = Green200)
            Spacer(Modifier.height(12.dp))
            Text(
                modifier = Modifier.clickable { onReportClick() },
                text = "신고 내역",
                style = body2Regular,
                color = Gray600
            )
            Spacer(Modifier.height(12.dp))
            Text("최근 플로깅 루트", style = body2Regular, color = Gray600)
            Spacer(Modifier.height(12.dp))
            Text("숲길 보기", style = body2Regular, color = Gray600)
            Spacer(Modifier.height(12.dp))
            Text("설정", style = title2Semi, color = Green200)
            Spacer(Modifier.height(12.dp))
            Text("서비스 이용약관", style = body2Regular, color = Gray600)
            Spacer(Modifier.height(12.dp))
            Text("개인정보 처리방침", style = body2Regular, color = Gray600)
            Spacer(Modifier.height(12.dp))
            Text("고객센터", style = body2Regular, color = Gray600)
            Spacer(Modifier.height(12.dp))
            Row {
                Text("버전 정보", style = body2Regular, color = Gray600)
                Spacer(Modifier.width(10.dp))
                Text("1.0.0", style = body2Regular, color = Gray450)
            }
            Spacer(Modifier.height(12.dp))
            Text("로그아웃", style = body2Regular, color = Gray600)
            Spacer(Modifier.height(12.dp))
            Text("회원 탈퇴", style = body2Regular, color = Gray600)
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
