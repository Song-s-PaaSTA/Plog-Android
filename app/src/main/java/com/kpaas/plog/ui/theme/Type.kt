package com.kpaas.plog.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kpaas.plog.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val pretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val pretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val pretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))
val pretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))

// Button
val button1Bold = TextStyle(
    fontFamily = pretendardBold,
    fontSize = 16.sp,
)
val button2Bold = TextStyle(
    fontFamily = pretendardBold,
    fontSize = 14.sp,
)
val button3Bold = TextStyle(
    fontFamily = pretendardBold,
    fontSize = 11.sp,
)
val button4Semi = TextStyle(
    fontFamily = pretendardSemiBold,
    fontSize = 8.sp,
)

// Title
val title1Bold = TextStyle(
    fontFamily = pretendardBold,
    fontSize = 30.sp,
)
val title2Semi = TextStyle(
    fontFamily = pretendardSemiBold,
    fontSize = 20.sp,
)
val title2Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 20.sp,
)
val title3Semi = TextStyle(
    fontFamily = pretendardSemiBold,
    fontSize = 16.sp,
)

// Body
val body1Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 16.sp,
)
val body1Semi = TextStyle(
    fontFamily = pretendardSemiBold,
    fontSize = 16.sp,
)
val body1Medium = TextStyle(
    fontFamily = pretendardMedium,
    fontSize = 16.sp,
)
val body2Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 14.sp,
)
val body2Medium = TextStyle(
    fontFamily = pretendardMedium,
    fontSize = 14.sp,
)
val body3Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 13.sp,
)
val body4Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 12.sp,
)
val body5Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 11.sp,
)
val body6Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 10.sp,
)
val body7Regular = TextStyle(
    fontFamily = pretendardRegular,
    fontSize = 8.sp,
)