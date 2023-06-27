package com.pixabay.imagesearch.data.repository

import com.pixabay.imagesearch.data.ImageItem
import com.pixabay.imagesearch.domain.mappers.MappedImageItemModel
import com.pixabay.imagesearch.domain.mappers.toImageModel
import com.pixabay.imagesearch.domain.repositories.ImageSearchRepository
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource/*, private val network: NetworkConnectivity*/
) : ImageSearchRepository {

    /*get data from network data source*/
    override suspend fun fetchSearchData(query: String): List<MappedImageItemModel> {
        return try {
            val result = networkDataSource.fetchSearchData(query = query)

            if(result.hits.isNullOrEmpty()){
                throw IllegalStateException("Empty product list")
            }
           result.hits.map {
                 it.toImageModel()
             }
//            ifEmpty {
//                throw IllegalStateException("Empty product list")
//            }

        } catch (e: Exception) {
            throw e
        }
    }
}