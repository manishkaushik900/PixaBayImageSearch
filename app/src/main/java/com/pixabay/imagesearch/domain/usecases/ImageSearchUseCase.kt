package com.pixabay.imagesearch.domain.usecases

import com.pixabay.imagesearch.domain.entities.MappedImageItemModel
import com.pixabay.imagesearch.domain.entities.repositories.ImageSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*Image Search use case*/
internal class ImageSearchUseCase @Inject constructor(
    private val repository: ImageSearchRepository
) {

    fun execute(query: String): Flow<List<MappedImageItemModel>> = flow {

        emit(repository.fetchSearchData(query)/*.map {
            it.toImageModel()
        }*/)
    }.flowOn(Dispatchers.IO)

}