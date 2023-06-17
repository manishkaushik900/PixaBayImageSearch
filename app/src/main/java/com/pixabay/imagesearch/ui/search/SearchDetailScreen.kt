package com.pixabay.imagesearch.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pixabay.imagesearch.R
import com.pixabay.imagesearch.data.remote.models.ImageItem
import com.pixabay.imagesearch.ui.commons.dummyImageItem
import com.pixabay.imagesearch.utils.ImageDownloader

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MaterialTheme {
        Surface {
            SearchImageDetails(dummyImageItem) {}
        }

    }

}

@Composable
fun SearchImageDetails(result: ImageItem, onBackClicked: () -> Unit) {

    val downloadManager = ImageDownloader(LocalContext.current)

    DetailContent(
        modifier = Modifier.fillMaxSize(),
        item = result,
        onBackClicked = onBackClicked,
        onDownloadClicked = { imageUrl ->
            downloadManager.downloadFile(imageUrl)
        }
    )
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    item: ImageItem,
    onBackClicked: () -> Unit,
    onDownloadClicked: (String) -> Unit
) {

    Surface(modifier = modifier) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current).data(item.largeImageURL)
                .crossfade(true).build(),
            contentDescription = item.user,
            contentScale = ContentScale.Crop/*,
            placeholder = painterResource(id = R.drawable.sample)*/
        )

        Column(
            Modifier
                .fillMaxSize()
        ) {
            BackButton(
                onBackClicked = onBackClicked
            )

            Spacer(modifier = Modifier.weight(1f))

            val color = Color.Black.copy(0.2f)
            DetailBottomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color),
                item = item,
                onDownloadClicked = onDownloadClicked
            )
        }
    }


}

@Composable
fun BackButton(
    modifier: Modifier = Modifier, onBackClicked: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onBackClicked() }) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = "Favorite",
            tint = MaterialTheme.colorScheme.surfaceTint
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomCard(
    modifier: Modifier = Modifier,
    item: ImageItem,
    onDownloadClicked: (String) -> Unit
) {


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BadgedBox(
            modifier = Modifier.padding(8.dp),
            badge = {
                Badge {
                    val badgeNumber = item.likes.toString()
                    Text(badgeNumber)
                }
            }) {
            Icon(
                Icons.Outlined.Favorite,
                contentDescription = stringResource(R.string.label_image_likes),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        BadgedBox(
            modifier = Modifier.padding(8.dp),
            badge = {
                Badge {
                    val badgeNumber = item.comments.toString()
                    Text(
                        badgeNumber,
                        modifier = Modifier.semantics {
                            contentDescription = "$badgeNumber new notifications"
                        }
                    )
                }
            }) {
            Icon(
                Icons.Outlined.Comment,
                contentDescription = "Favorite",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        BadgedBox(
            modifier = Modifier.padding(8.dp),
            badge = {
                Badge {
                    val badgeNumber = item.views.toString()
                    Text(
                        badgeNumber,
                        modifier = Modifier.semantics {
                            contentDescription = "$badgeNumber new notifications"
                        }
                    )
                }
            }) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = "Favorite",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.padding(8.dp),
            onClick = {
                onDownloadClicked(item.largeImageURL.toString())

            }
        ) {
            Icon(
                Icons.Filled.Download,
                contentDescription = stringResource(R.string.label_download_image),
                tint = Color.White
            )
        }


    }

}


