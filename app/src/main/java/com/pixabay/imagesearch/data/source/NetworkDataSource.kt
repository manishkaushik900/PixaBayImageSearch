package com.pixabay.imagesearch.data.source

import com.manish.dkb.data.remote.ApiService
import com.pixabay.imagesearch.data.remote.models.PixabayResponse
import javax.inject.Inject

/*Network data source to fetch data from server using api service client*/
class NetworkDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun callImageSearchApi(query: String): PixabayResponse = apiService.searchImages(query)
}