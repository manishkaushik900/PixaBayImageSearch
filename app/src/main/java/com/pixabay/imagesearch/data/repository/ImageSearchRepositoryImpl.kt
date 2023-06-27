package com.pixabay.imagesearch.data.repository

import com.pixabay.imagesearch.data.entities.toImageModel
import com.pixabay.imagesearch.domain.entities.MappedImageItemModel
import com.pixabay.imagesearch.domain.repositories.ImageSearchRepository
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource/*, private val network: NetworkConnectivity*/
) : ImageSearchRepository {

    /*get data from network data source*/
    override suspend fun fetchSearchData(query: String): List<MappedImageItemModel> {
        return try {
            val result = networkDataSource.fetchSearchData(query = query)

            if(result.hits.isEmpty()){
                throw IllegalStateException("Empty product list")
            }
           result.hits.map {
                 it.toImageModel()
             }
        } catch (e: Exception) {
            throw e
        }
    }
}