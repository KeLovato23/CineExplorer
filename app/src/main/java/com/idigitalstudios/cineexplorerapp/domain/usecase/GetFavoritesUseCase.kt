package com.idigitalstudios.cineexplorerapp.domain.usecase

import com.idigitalstudios.cineexplorerapp.domain.model.Show
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: ShowRepository) {
    operator fun invoke(): Flow<List<Show>> = repository.getFavorites()
}
