package com.pixabay.imagesearch.domain.repositories

import com.pixabay.imagesearch.data.remote.ImageItem

interface ImageSearchRepository {

    suspend fun fetchSearchData(query: String): List<ImageItem>


}