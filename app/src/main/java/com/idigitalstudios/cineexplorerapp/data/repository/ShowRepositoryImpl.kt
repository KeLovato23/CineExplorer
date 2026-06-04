package com.idigitalstudios.cineexplorerapp.data.repository

import com.idigitalstudios.cineexplorerapp.data.local.ShowDao
import com.idigitalstudios.cineexplorerapp.data.mapper.toDomain
import com.idigitalstudios.cineexplorerapp.data.mapper.toEntity
import com.idigitalstudios.cineexplorerapp.data.remote.TvMazeApi
import com.idigitalstudios.cineexplorerapp.domain.model.CastMember
import com.idigitalstudios.cineexplorerapp.domain.model.Episode
import com.idigitalstudios.cineexplorerapp.domain.model.Show
import com.idigitalstudios.cineexplorerapp.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowRepositoryImpl @Inject constructor(
    private val api: TvMazeApi,
    private val dao: ShowDao
) : ShowRepository {

    override fun getCachedShows(): Flow<List<Show>> =
        dao.getAllShows().map { list -> list.map { it.toDomain() } }

    override suspend fun fetchAndCacheShows(page: Int): Result<Unit> = runCatching {
        val remote = api.getShows(page)
        val favoriteIds = dao.getFavoriteIds().toSet()
        val entities = remote.map { dto -> dto.toEntity(isFavorite = favoriteIds.contains(dto.id)) }
        dao.upsertShows(entities)
    }

    override suspend fun getShowDetail(id: Int): Result<Show> = runCatching {
        val cached = dao.getShowById(id)
        if (cached != null) return@runCatching cached.toDomain()
        val remote = api.getShowDetail(id)
        val entity = remote.toEntity()
        dao.upsertShow(entity)
        entity.toDomain()
    }

    override fun getFavorites(): Flow<List<Show>> =
        dao.getFavorites().map { list -> list.map { it.toDomain() } }

    override suspend fun toggleFavorite(show: Show) {
        dao.updateFavorite(show.id, !show.isFavorite)
    }

    override suspend fun getCast(showId: Int): Result<List<CastMember>> = runCatching {
        api.getCast(showId).map { it.toDomain() }
    }

    override suspend fun getEpisodes(showId: Int): Result<List<Episode>> = runCatching {
        api.getEpisodes(showId).map { it.toDomain() }
    }
}
