package com.pixabay.imagesearch.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pixabay.imagesearch.domain.entities.ImageItem
import com.pixabay.imagesearch.domain.mappers.MappedImageItemModel
import com.pixabay.imagesearch.domain.mappers.toImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object SamplePixabayProvider : PreviewParameterProvider<ImageItem> {
    override val values: Sequence<ImageItem> = sequenceOf(
        ImageItem(
            webformatHeight = 426,
            imageWidth = 4752,
            previewHeight = 99,
            webformatURL = "https://pixabay.com/get/g62eef811a34bd9c15c9269ec772b3729e13b4c75a1578286df917a2397618f7c81e92e56163c97ff272a7e863e09f406_640.jpg",
            userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
            previewURL = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_150.jpg",
            comments = 389,
            imageHeight = 3168,
            tags = "berries,fruits, food",
            previewWidth = 150,
            downloads = 502431,
            collections = 1780,
            largeImageURL = "https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg",
            views = 944821,
            likes = 1889,
            userId = 1
        ),
        ImageItem(
            webformatHeight = 426,
            imageWidth = 4752,
            previewHeight = 99,
            webformatURL = "https://pixabay.com/get/g62eef811a34bd9c15c9269ec772b3729e13b4c75a1578286df917a2397618f7c81e92e56163c97ff272a7e863e09f406_640.jpg",
            userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
            previewURL = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_150.jpg",
            comments = 389,
            imageHeight = 3168,
            tags = "berries,fruits, food",
            previewWidth = 150,
            downloads = 502431,
            collections = 1780,
            largeImageURL = "https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg",
            views = 944821,
            likes = 1889,
            userId = 2
        )
    )

    fun returnPixabayResponse(): Flow<List<MappedImageItemModel>> {
        return flowOf(values.toList().map { imageItem -> imageItem.toImageModel() })
    }
    fun returnPixabayMalformedResponse(): Flow<List<MappedImageItemModel>> {
         throw IllegalStateException("Empty product list")
    }
}