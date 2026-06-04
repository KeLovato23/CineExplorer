package com.idigitalstudios.cineexplorerapp.domain.usecase

import com.idigitalstudios.cineexplorerapp.domain.model.Episode
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val repository: ShowRepository) {
    suspend operator fun invoke(showId: Int): Result<List<Episode>> = repository.getEpisodes(showId)
}
