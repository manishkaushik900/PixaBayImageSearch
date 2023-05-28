package com.pixabay.imagesearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pixabay.imagesearch.R
import com.pixabay.imagesearch.data.remote.models.ImageModel
import com.pixabay.imagesearch.ui.theme.PixaBayImageSearchTheme
import com.pixabay.imagesearch.ui.viewmodels.ImageSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ImageSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAlbumList("brands")

        setContent {
            PixaBayImageSearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    FetchData(viewModel)
                }
            }
        }
    }

    @Composable
    private fun FetchData(viewModel: ImageSearchViewModel) {
        when (val result = viewModel.response.value) {
            is ImageSearchViewModel.AlbumUiState.Error -> {
                Text(text = result.message)
            }

            ImageSearchViewModel.AlbumUiState.Loading -> {
                CircularProgressIndicator()
            }

            is ImageSearchViewModel.AlbumUiState.Success -> {
                LazyVerticalGrid(
                    // on below line we are setting the
                    // column count for our grid view.
                    columns = GridCells.Fixed(2),
                    // on below line we are adding padding
                    // from all sides to our grid view.
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(result.albumList.size) {
                        ImageCard(result.albumList[it])
                    }
                }

            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ImageCard(
        item: ImageModel, modifier: Modifier = Modifier
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = { }
        ) {
            Box(modifier = Modifier.height(200.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.largeImageURL)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.sample),
                    contentDescription = item.userName,
                    contentScale = ContentScale.Crop,
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ), startY = 350f
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp), contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = item.userName,
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
                }

            }

        }

    }




    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PixaBayImageSearchTheme {
            val painter = painterResource(id = R.drawable.ic_launcher_background)
            val contentDescription = "Lorem Ipsum"
            val title = "Lorem Ipsum"
//        ImageCard(painter = painter,
//            contentDescription = contentDescription, title = title)
        }
    }
}