package com.idigitalstudios.cineexplorerapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.idigitalstudios.cineexplorerapp.data.local.entity.ShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    @Query("SELECT * FROM shows ORDER BY name ASC")
    fun getAllShows(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM shows WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): Flow<List<ShowEntity>>

    @Query("SELECT id FROM shows WHERE isFavorite = 1")
    suspend fun getFavoriteIds(): List<Int>

    @Query("SELECT * FROM shows WHERE id = :id")
    suspend fun getShowById(id: Int): ShowEntity?

    @Upsert
    suspend fun upsertShows(shows: List<ShowEntity>)

    @Upsert
    suspend fun upsertShow(show: ShowEntity)

    @Query("UPDATE shows SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}
