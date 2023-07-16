package com.pixabay.imagesearch.ui.searchImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.imagesearch.domain.entities.MappedImageItemModel
import com.pixabay.imagesearch.domain.usecases.ImageSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val useCase: ImageSearchUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(SearchImageState())

    fun handleEvent(imageSearchEvent: SearchImageEvent) {
        when (imageSearchEvent) {
            is SearchImageEvent.ErrorDismissed -> {
                dismissError()
            }

            is SearchImageEvent.InitiateSearch -> {
                fetchData(imageSearchEvent.query)
            }

            is SearchImageEvent.QueryChanged -> {
                updateQuery(imageSearchEvent.query)
            }

            is SearchImageEvent.OnError -> {
                onError(imageSearchEvent.error)
            }

            is SearchImageEvent.UpdateCurrentItem -> {
                updateCurrentImage(imageSearchEvent.item)
            }
        }
    }

    private fun updateCurrentImage(item: MappedImageItemModel) {
        uiState.value = uiState.value.copy(
            currentImageNode = item
        )
    }

    private fun dismissError() {
        uiState.value = uiState.value.copy(
            error = null,
            currentImageNode = null
        )

    }


    private fun fetchData(query: String) {

        uiState.value = uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            useCase.execute(query).catch { error ->
                handleEvent(SearchImageEvent.OnError(error.message.toString()))
            }.collect { result ->
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    success = result,
                    currentImageNode = null
                )
            }
        }
    }

    private fun updateQuery(query: String) {
        if (query.isEmpty()) {
            uiState.value = uiState.value.copy(
                success = emptyList(),
                query = null,
                currentImageNode = null
            )
        } else {
            uiState.value = uiState.value.copy(
                query = query,
                currentImageNode = null
            )
        }
    }


    private fun onError(error: String) {
        uiState.value = uiState.value.copy(
            isLoading = false,
            error = error,
            success = emptyList(),
            currentImageNode = null
        )
    }

}
