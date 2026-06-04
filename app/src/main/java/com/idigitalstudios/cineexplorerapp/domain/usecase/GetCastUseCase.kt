package com.idigitalstudios.cineexplorerapp.domain.usecase

import com.idigitalstudios.cineexplorerapp.domain.model.CastMember
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import javax.inject.Inject

class GetCastUseCase @Inject constructor(private val repository: ShowRepository) {
    suspend operator fun invoke(showId: Int): Result<List<CastMember>> = repository.getCast(showId)
}
