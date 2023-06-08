package com.pixabay.imagesearch.ui

import com.pixabay.imagesearch.data.remote.models.ImageItem
import com.pixabay.imagesearch.data.remote.models.ImageModel


data class ImageSearchState(

    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<ImageItem> = emptyList(),
    val query: String? = null,
)

sealed class ImageSearchEvent {

    object ErrorDismissed : ImageSearchEvent()

    class InitiateSearch(val query: String) : ImageSearchEvent()

    class QueryChanged(val query: String) :
        ImageSearchEvent()

}