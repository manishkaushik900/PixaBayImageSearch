package com.pixabay.imagesearch.data.mappers

import com.pixabay.imagesearch.data.remote.ImageItem

data class ImageModel(
    val imageId: Long = -1,
    val userName: String,
    val url: String,
    val likes: String,
    val downloads: String,
    val comments: String,
    val tags: List<String>,
    val largeImageURL: String?,
    val previewURL:String?,
    val userImageURL: String?
)



fun ImageItem.toImageModel() = ImageModel(
    imageId = id?.toLong() ?: -1,
    userName = user ?: "",
    url = previewURL ?: "",
    likes = likes.toString(),
    downloads = downloads.toString(),
    comments = comments.toString(),
    tags = tags?.split(", ") ?: emptyList(),
    largeImageURL = largeImageURL,
    previewURL = previewURL,
    userImageURL= userImageURL
)