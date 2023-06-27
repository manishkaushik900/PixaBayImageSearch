package com.pixabay.imagesearch.data

import android.os.Parcelable
import com.pixabay.imagesearch.domain.mappers.MappedImageItemModel
import kotlinx.android.parcel.Parcelize


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