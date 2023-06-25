package com.pixabay.imagesearch.data.repository

import com.pixabay.imagesearch.data.remote.ApiService
import com.pixabay.imagesearch.domain.entities.PixabayResponse
import javax.inject.Inject

/*Network data source to fetch data from server using api service client*/
class NetworkDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchSearchData(query: String): PixabayResponse = apiService.getSearchResponse(query)
}