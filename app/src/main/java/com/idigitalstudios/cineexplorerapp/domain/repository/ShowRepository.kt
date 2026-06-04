package com.idigitalstudios.cineexplorerapp.domain.repository

import com.idigitalstudios.cineexplorerapp.domain.model.CastMember
import com.idigitalstudios.cineexplorerapp.domain.model.Episode
import com.idigitalstudios.cineexplorerapp.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    fun getCachedShows(): Flow<List<Show>>
    suspend fun fetchAndCacheShows(page: Int): Result<Unit>
    suspend fun getShowDetail(id: Int): Result<Show>
    fun getFavorites(): Flow<List<Show>>
    suspend fun toggleFavorite(show: Show)
    suspend fun getCast(showId: Int): Result<List<CastMember>>
    suspend fun getEpisodes(showId: Int): Result<List<Episode>>
}
