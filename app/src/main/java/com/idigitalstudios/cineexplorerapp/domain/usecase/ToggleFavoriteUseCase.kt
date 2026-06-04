package com.idigitalstudios.cineexplorerapp.domain.usecase

import com.idigitalstudios.cineexplorerapp.domain.model.Show
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: ShowRepository) {
    suspend operator fun invoke(show: Show) = repository.toggleFavorite(show)
}
