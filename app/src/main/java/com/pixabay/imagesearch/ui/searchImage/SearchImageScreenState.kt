package com.pixabay.imagesearch.ui.searchImage

import com.pixabay.imagesearch.domain.entities.MappedImageItemModel


data class SearchImageState(

    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<MappedImageItemModel> = emptyList(),
    val query: String? = null,
    val currentImageNode: MappedImageItemModel? = null
)

sealed class SearchImageEvent {

    object ErrorDismissed : SearchImageEvent()

    class InitiateSearch(val query: String) : SearchImageEvent()

    class QueryChanged(val query: String) :
        SearchImageEvent()

    class OnError(val error: String) :
        SearchImageEvent()

    class UpdateCurrentItem(val item: MappedImageItemModel) :
        SearchImageEvent()

   /* class UpdateCurrentImageNode(val imageItem: ImageItem) :
        ImageSearchEvent()*/

}