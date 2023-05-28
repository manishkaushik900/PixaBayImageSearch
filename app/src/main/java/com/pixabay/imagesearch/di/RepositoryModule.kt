package com.pixabay.imagesearch.di

import com.pixabay.imagesearch.data.repository.ImageSearchRepositoryImpl
import com.pixabay.imagesearch.domain.repository.ImageSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindScheduleRepository(albumRepositoryImpl: ImageSearchRepositoryImpl): ImageSearchRepository

}