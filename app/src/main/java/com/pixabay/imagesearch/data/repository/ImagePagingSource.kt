package com.pixabay.imagesearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pixabay.imagesearch.domain.entities.MappedImageItemModel

class ImagePagingSource: PagingSource<Int, MappedImageItemModel>() {
    override fun getRefreshKey(state: PagingState<Int, MappedImageItemModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MappedImageItemModel> {
        TODO("Not yet implemented")
    }
}