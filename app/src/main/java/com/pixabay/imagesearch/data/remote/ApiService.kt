package com.pixabay.imagesearch.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(".")
    suspend fun getSearchResponse(
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
        @Query("pretty") pretty: Boolean = true
    ): PixabayResponse
}
