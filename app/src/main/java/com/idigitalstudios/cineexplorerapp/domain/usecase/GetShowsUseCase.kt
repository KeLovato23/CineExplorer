package com.idigitalstudios.cineexplorerapp.domain.usecase

import com.idigitalstudios.cineexplorerapp.domain.model.Show
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(private val repository: ShowRepository) {
    fun getCached(): Flow<List<Show>> = repository.getCachedShows()
    suspend fun refresh(page: Int = 0): Result<Unit> = repository.fetchAndCacheShows(page)
}
