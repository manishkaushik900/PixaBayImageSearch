package com.pixabay.imagesearch.ui.commons

import com.pixabay.imagesearch.data.remote.models.ImageItem


data class ImageSearchState(

    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<ImageItem> = emptyList(),
    val query: String? = null/*,
    val currentImageNode: ImageItem? = null*/
)

sealed class ImageSearchEvent {

    object ErrorDismissed : ImageSearchEvent()

    class InitiateSearch(val query: String) : ImageSearchEvent()

    class QueryChanged(val query: String) :
        ImageSearchEvent()

   /* class UpdateCurrentImageNode(val imageItem: ImageItem) :
        ImageSearchEvent()*/

}