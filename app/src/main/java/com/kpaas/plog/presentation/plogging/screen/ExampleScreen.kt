package com.kpaas.plog.presentation.plogging.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kpaas.plog.core_ui.theme.White
import com.kpaas.plog.domain.entity.ExampleEntity
import com.kpaas.plog.presentation.plogging.navigation.PloggingNavigator
import com.kpaas.plog.presentation.plogging.viewmodel.ExampleViewModel
import com.kpaas.plog.util.UiState

@Composable
fun ExampleRoute(
    navigator: PloggingNavigator,
    exampleViewModel: ExampleViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        exampleViewModel.getUsers(2)
    }

    ExampleScreen(
        exampleViewModel = exampleViewModel
    )
}

@Composable
fun ExampleScreen(
    exampleViewModel: ExampleViewModel
) {
    val getExampleState by exampleViewModel.getExampleState.collectAsStateWithLifecycle(UiState.Empty)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        when (getExampleState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UiState.Success -> {
                val data = (getExampleState as UiState.Success<List<ExampleEntity>>).data
                LazyColumn {
                    itemsIndexed(data) { index, item ->
                        ExampleItem(data = item)
                    }
                }
            }

            is UiState.Failure -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Failed to load calendar data",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {

            }
        }
    }
}

@Preview
@Composable
fun ExampleScreenPreview() {
    ExampleScreen(exampleViewModel = hiltViewModel())
}