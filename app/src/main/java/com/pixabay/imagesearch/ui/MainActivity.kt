package com.pixabay.imagesearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pixabay.imagesearch.R
import com.pixabay.imagesearch.data.remote.models.ImageItem
import com.pixabay.imagesearch.data.remote.models.ImageModel
import com.pixabay.imagesearch.ui.viewmodels.ImageSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ImageSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.getAlbumList("brands")

        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreviewtwo() {
        MaterialTheme {
            MainScreen()
        }

    }

    @Composable
    fun MainScreen() {

        val viewModel: ImageSearchViewModel = viewModel

        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            handleEvent = viewModel::handleEvent,
            searchState = viewModel.uiState.collectAsState().value
        )

    }

    @Composable
    fun ScreenContent(
        modifier: Modifier = Modifier,
        searchState: ImageSearchState,
        handleEvent: (event: ImageSearchEvent) -> Unit
    ) {

        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {

            if (searchState.isLoading) {
                CircularProgressIndicator()
            } else {

                Column(modifier = modifier) {

                    SearchInput(
                        modifier = Modifier.fillMaxWidth(),
                        query = searchState.query,
                        onSearchChange = { queryChanged ->
                            handleEvent(ImageSearchEvent.QueryChanged(queryChanged))
                        },
                        onSearchClicked = {
                            handleEvent(
                                ImageSearchEvent.InitiateSearch(
                                    searchState.query ?: ""
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        // on below line we are setting the
                        // column count for our grid view.
                        columns = GridCells.Fixed(2),
                        // on below line we are adding padding
                        // from all sides to our grid view.
                        modifier = Modifier.padding(10.dp)
                    ) {
                        items(searchState.success.size) {
                            ImageCard(
                                modifier = Modifier.fillMaxWidth(),
                                item = searchState.success[it]
                            )
                        }
                    }

                }


                searchState.error?.let { error ->
                    AuthenticationErrorDialog(
                        error = error,
                        dismissError = {
                            handleEvent(
                                ImageSearchEvent.ErrorDismissed
                            )
                        }
                    )
                }


            }


        }

    }

    @Composable
    fun AuthenticationErrorDialog(
        modifier: Modifier = Modifier,
        error: String,
        dismissError: () -> Unit
    ) {

        AlertDialog(
            modifier = modifier.testTag(""),
            onDismissRequest = { dismissError() },
            confirmButton = {
                TextButton(
                    onClick = {
                        dismissError()
                    }
                ) {
                    Text(text = stringResource(id = R.string.error_action))
                }
            },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.error_title
                    ),
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = error
                )
            }

        )

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ImageCard(
        item: ImageItem, modifier: Modifier = Modifier
    ) {
        Card(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = { }) {
            Box(modifier = Modifier.height(200.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(item.largeImageURL)
                        .crossfade(true).build(),
                    placeholder = painterResource(R.drawable.sample),
                    contentDescription = item.user,
                    contentScale = ContentScale.Crop,
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, Color.Black
                                ), startY = 350f
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {

                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = item.user?:"",
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )
//                        Text(text = "SemicolonSpace 2")
//                        Text(text = "SemicolonSpace 3")
                    }
                }

            }

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchInput(
        modifier: Modifier = Modifier,
        query: String?,
        onSearchChange: (query: String) -> Unit,
        onSearchClicked: () -> Unit
    ) {
        TextField(modifier = modifier.testTag(""),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ), keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked()
            }), value = query ?: "",
            onValueChange = {
                onSearchChange(it)

            }, label = {
                Text(text = stringResource(id = R.string.label_search))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null
                )
            }, singleLine = true
        )
    }

    /*@Composable
    private fun FetchData(viewModel: ImageSearchViewModel, text: String) {
        viewModel.getAlbumList(text)
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
    }*/


}