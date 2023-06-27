package com.pixabay.imagesearch.data.entities

import com.pixabay.imagesearch.domain.entities.MappedImageItemModel


fun ImageItem.toImageModel() = MappedImageItemModel(
    imageId = id?.toLong() ?: -1,
    user = user ?: "",
    url = previewURL ?: "",
    views = views.toString(),
    likes = likes.toString(),
    downloads = downloads.toString(),
    comments = comments.toString(),
    tags = tags?.split(", ") ?: emptyList(),
    largeImageURL = largeImageURL,
    previewURL = previewURL,
    userImageURL= userImageURL
)