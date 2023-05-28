package com.pixabay.imagesearch.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manish.dkb.domain.usecases.ImageSearchUseCase
import com.manish.dkb.domain.util.Resource
import com.pixabay.imagesearch.data.remote.models.ImageItem
import com.pixabay.imagesearch.data.remote.models.ImageModel
import com.pixabay.imagesearch.data.remote.models.toImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val useCase: ImageSearchUseCase
) : ViewModel() {

//    private val _uiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Loading)
//    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()

     val response: MutableState<AlbumUiState> = mutableStateOf(AlbumUiState.Loading)

    init {
        getAlbumList("brands")
    }

    /*get album list from the server*/
    fun getAlbumList(query:String) {
        viewModelScope.launch {
            useCase.execute(query).onStart {
                response.value = AlbumUiState.Loading
        }.catch {
            response.value = AlbumUiState.Error(it.message.toString())
        }.collect {


                when (it) {
                is Resource.Success -> {
                 val items =    it.data.hits?.let { data ->
                        data.map { list -> list.toImageModel() }
                    }?: listOf()
                    response.value = AlbumUiState.Success(items)
                }
                is Resource.Error -> {
                    response.value = AlbumUiState.Error(it.message)
                }
            }

        }
//            _uiState.value = AlbumUiState.Loading
//            when (val result = useCase.execute(query)) {
//                is Resource.Success -> {
//                    result.data.hits?.let { data ->
//                        data.map { list -> list.toImageModel() }
//                        _uiState.value = AlbumUiState.AlbumListLoaded(data)
//                    }
//                }
//                is Resource.Error -> {
//                    result.message.let { error ->
//                        _uiState.emit(AlbumUiState.Error(error))
//                    }
//                }
//            }
                /*   albumListUseCase.execute().collect { results ->
                _uiState.update {
                    when (results) {
                        is Resource.Success -> {
                            results.data?.let { data ->
                                AlbumUiState.AlbumListLoaded(data)
                            } ?: AlbumUiState.Error("Unknown Error Occurred!")
                        }
                        is Resource.Error -> AlbumUiState.Error(results.message!!)

                    }

                }
            }*/

        }
    }





sealed class AlbumUiState {
    object Loading : AlbumUiState()
    data class Success(val albumList: List<ImageModel>) : AlbumUiState()
    data class Error(val message: String) : AlbumUiState()
}
}

//sealed class AlbumUiState {
//    class Success (val data: List<Post>): AlbumUiState()
//    class Failure(val msg: Throwable):AlbumUiState()
//    object Loading:AlbumUiState()
//    object Empty:AlbumUiState()
//}
