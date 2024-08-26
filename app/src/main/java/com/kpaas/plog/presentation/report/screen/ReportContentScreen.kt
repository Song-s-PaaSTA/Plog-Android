package com.kpaas.plog.presentation.report.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kpaas.plog.presentation.report.navigation.ReportNavigator

@Composable
fun ReportContentRoute(
    navigator: ReportNavigator,
    id: Int,
) {
    ReportContentScreen(
        id = id
    )
}

@Composable
fun ReportContentScreen(
    id: Int,
) {

}

@Preview
@Composable
fun PreviewReportContentScreen() {
    ReportContentScreen(
        id = 0,
    )
}