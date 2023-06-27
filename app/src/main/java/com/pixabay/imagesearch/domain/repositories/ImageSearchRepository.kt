package com.pixabay.imagesearch.domain.repositories

import com.pixabay.imagesearch.domain.entities.MappedImageItemModel

interface ImageSearchRepository {

    suspend fun fetchSearchData(query: String): List<MappedImageItemModel>


}