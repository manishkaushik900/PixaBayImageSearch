package com.pixabay.imagesearch.domain.repository

import com.manish.dkb.domain.util.Resource
import com.pixabay.imagesearch.data.remote.models.PixabayResponse

interface ImageSearchRepository {

    suspend fun getImageSearchData(query:String): Resource<PixabayResponse>

    suspend fun getImageSearchDataFlow(query:String): PixabayResponse


}