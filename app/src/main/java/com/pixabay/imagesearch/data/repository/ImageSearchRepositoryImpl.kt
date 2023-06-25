package com.pixabay.imagesearch.data.repository

import com.pixabay.imagesearch.domain.entities.ImageItem
import com.pixabay.imagesearch.domain.repositories.ImageSearchRepository
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource/*, private val network: NetworkConnectivity*/
) : ImageSearchRepository {

    /*get data from network data source*/
    override suspend fun fetchSearchData(query: String): List<ImageItem> {
        return try {
            val result = networkDataSource.fetchSearchData(query = query)

            result.hits.ifEmpty {
                throw IllegalStateException("Empty product list")
            }

        } catch (e: Exception) {
            throw e
        }
    }
}