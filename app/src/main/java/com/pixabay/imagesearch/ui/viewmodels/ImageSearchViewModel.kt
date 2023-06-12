package com.pixabay.imagesearch.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manish.dkb.domain.usecases.ImageSearchUseCase
import com.pixabay.imagesearch.data.remote.models.ImageItem
import com.pixabay.imagesearch.ui.ImageSearchEvent
import com.pixabay.imagesearch.ui.ImageSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val useCase: ImageSearchUseCase
) : ViewModel() {

//    private val _uiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Loading)
//    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()

    val uiState = MutableStateFlow(ImageSearchState())


    public fun SaveImage(){


    }

    private fun updateQuery(query: String) {

        if (query.isEmpty()) {
            uiState.value = uiState.value.copy(
                success = emptyList<ImageItem>(),
                query = query
            )
        } else {
            uiState.value = uiState.value.copy(
                query = query
            )
        }
    }


    fun handleEvent(imageSearchEvent: ImageSearchEvent) {
        when (imageSearchEvent) {
            is ImageSearchEvent.ErrorDismissed -> {
                dismissError()
            }

            is ImageSearchEvent.InitiateSearch -> {
                fetchData(imageSearchEvent.query)
            }

            is ImageSearchEvent.QueryChanged -> {
                updateQuery(imageSearchEvent.query)
            }
        }
    }

    private fun fetchData(query: String) {

        uiState.value = uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.IO) {


            useCase.execute(query).catch { error ->

                withContext(Dispatchers.Main) {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = error.message.toString()
                    )
                }
            }.collect { result ->

                withContext(Dispatchers.Main) {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = result.hits/*.map {
                            it.toImageModel()
                        }*/
                    )
                }

            }


        }


    }

    private fun dismissError() {
        uiState.value = uiState.value.copy(
            error = null
        )

    }
}
