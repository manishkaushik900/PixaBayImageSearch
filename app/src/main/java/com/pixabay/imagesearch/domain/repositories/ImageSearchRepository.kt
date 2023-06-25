package com.pixabay.imagesearch.domain.repositories

import com.pixabay.imagesearch.domain.entities.ImageItem

interface ImageSearchRepository {

    suspend fun fetchSearchData(query: String): List<ImageItem>


}