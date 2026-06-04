package com.idigitalstudios.cineexplorerapp.di

import com.idigitalstudios.cineexplorerapp.data.repository.ShowRepositoryImpl
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShowRepository(impl: ShowRepositoryImpl): ShowRepository
}
