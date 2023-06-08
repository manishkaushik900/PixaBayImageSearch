package com.pixabay.imagesearch.data.repository

import com.manish.dkb.domain.util.Resource
import com.pixabay.imagesearch.data.remote.models.PixabayResponse
import com.pixabay.imagesearch.data.source.NetworkDataSource
import com.pixabay.imagesearch.domain.repository.ImageSearchRepository
import com.pixabay.imagesearch.utils.NetworkConnectivity
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource, private val network: NetworkConnectivity
) : ImageSearchRepository {

    /*get data from network data source*/
    override suspend fun getImageSearchData(query:String): Resource<PixabayResponse> {
        return try {
            val result = networkDataSource.callImageSearchApi(query = query)

            if (result.hits?.isNotEmpty() == true) {
                Resource.Success(result)
            } else {
                Resource.Error(IllegalStateException("Empty product list").message.toString())
            }

        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    }


    override suspend fun getImageSearchDataFlow(query:String): PixabayResponse {
        return try {
            val result = networkDataSource.callImageSearchApi(query = query)

            if (result.hits?.isNotEmpty() == true) {
                result
            } else {
               throw IllegalStateException("Empty product list")
            }

        } catch (e: Exception) {
            throw e

        }

    }
}