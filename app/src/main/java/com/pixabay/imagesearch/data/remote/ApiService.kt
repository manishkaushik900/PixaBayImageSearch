package com.manish.dkb.data.remote

import com.pixabay.imagesearch.data.remote.models.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(".")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
        @Query("pretty") pretty: Boolean = true
    ): PixabayResponse
}
