package com.manish.dkb.domain.usecases

import com.manish.dkb.domain.util.Resource
import com.pixabay.imagesearch.data.remote.models.PixabayResponse
import com.pixabay.imagesearch.domain.repository.ImageSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/*Image Search use case*/
class ImageSearchUseCase @Inject constructor(
    private val repository: ImageSearchRepository
) {

    fun execute(query: String): Flow<Resource<PixabayResponse>> = flow{
       emit(repository.getImageSearchData(query))
    }.flowOn(Dispatchers.IO)

//    suspend fun execute(query: String): Resource<PixabayResponse> {
//        return withContext(Dispatchers.IO) {
//            repository.getImageSearchData(query)
//        }
//    }


//    suspend fun execute(): Flow<Resource<List<AlbumDtoItem>>> = flow {
//        emit(repository.getAlbumList())
//    }.flowOn(ioDispatcher)

}