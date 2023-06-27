package com.pixabay.imagesearch.domain.repositories

import com.pixabay.imagesearch.domain.mappers.MappedImageItemModel

interface ImageSearchRepository {

    suspend fun fetchSearchData(query: String): List<MappedImageItemModel>


}