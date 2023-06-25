import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pixabay.imagesearch.R
import com.pixabay.imagesearch.data.remote.ImageItem
import com.pixabay.imagesearch.ui.commons.AnimatedShimmer
import com.pixabay.imagesearch.ui.searchImage.SearchImageEvent
import com.pixabay.imagesearch.ui.searchImage.SearchImageState
import com.pixabay.imagesearch.ui.searchImage.ImageSearchViewModel



@Composable
fun SearchScreen(onImageClicked: (item: ImageItem) -> Unit) {
    val viewModel: ImageSearchViewModel = hiltViewModel()

    ScreenScreenContent(
        modifier = Modifier.fillMaxSize(),
        handleEvent = viewModel::handleEvent,
        uiState = viewModel.uiState.collectAsState().value,
        onImageItemClicked = onImageClicked
    )
}

@Composable
fun ScreenScreenContent(
    modifier: Modifier = Modifier,
    uiState: SearchImageState,
    handleEvent: (event: SearchImageEvent) -> Unit,
    onImageItemClicked: (item: ImageItem) -> Unit
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {

        if (uiState.isLoading) {
            Column {
                repeat(7) {
                    AnimatedShimmer()
                }
            }

        } else {

            Column(modifier = modifier) {

                SearchInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp, top = 8.dp, bottom = 4.dp
                        ),
                    query = uiState.query,
                    onSearchChange = { queryChanged ->
                        handleEvent(SearchImageEvent.QueryChanged(queryChanged))
                    },
                    onSearchClicked = {
                        handleEvent(
                            SearchImageEvent.InitiateSearch(
                                uiState.query ?: ""
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                LazyVerticalGrid(
                    // on below line we are setting the
                    // column count for our grid view.
                    columns = GridCells.Fixed(2),
                    // on below line we are adding padding
                    // from all sides to our grid view.
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(uiState.success.size) {
                        ImageCard(
                            modifier = Modifier.fillMaxWidth(),
                            item = uiState.success[it],
                            onNextScreen = onImageItemClicked
                        )
                    }
                }

            }


            uiState.error?.let { error ->
                AuthenticationErrorDialog(
                    error = error,
                    dismissError = {
                        handleEvent(
                            SearchImageEvent.ErrorDismissed
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
    item: ImageItem, modifier: Modifier = Modifier, onNextScreen: (item: ImageItem) -> Unit
) {
//    val context = LocalContext.current
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onNextScreen(item)
//            Toast.makeText(context, item.user, Toast.LENGTH_SHORT).show()
        }) {
        Box(modifier = Modifier.height(200.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(item.largeImageURL)
                    .crossfade(true).build(),
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

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.user ?: "",
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
    TextField(modifier = modifier
        .testTag("")
        .background(MaterialTheme.colorScheme.background),
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
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = stringResource(id = R.string.cd_clear_search)
                ) {
                    onSearchChange("")
                },
                imageVector = Icons.Default.Cancel, contentDescription = null
            )
        }, singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MaterialTheme {
        SearchScreen(onImageClicked = {})
    }
}