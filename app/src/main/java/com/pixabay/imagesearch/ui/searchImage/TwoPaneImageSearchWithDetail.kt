package com.pixabay.imagesearch.ui.searchImage

import SearchScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageSearchWithDetailTwoPane(viewModel: ImageSearchViewModel, replyUiState: SearchImageState) {
    Column {
        Row {
            // Left pane content
            Surface(modifier = Modifier.weight(1f)) {
                // Content for the left pane
                SearchScreen(viewModel, onImageClicked = { imageItem ->
                    viewModel.handleEvent(SearchImageEvent.UpdateCurrentItem(imageItem))
                })
            }

            Spacer(modifier = Modifier
                .fillMaxHeight()
                .width(4.dp))
            // Right pane content
            Surface(modifier = Modifier.weight(1f)) {
                // Content for the right pane
                replyUiState.currentImageNode?.let {
                    ImageDetailScreen(1, it) {}
                } ?: kotlin.run {
                    EmptyCompose()
                }
            }
        }
    }
}

@Composable
fun EmptyCompose() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text("NO data to show!")
    }
}