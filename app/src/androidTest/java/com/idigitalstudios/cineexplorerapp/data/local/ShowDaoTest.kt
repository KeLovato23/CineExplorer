package com.idigitalstudios.cineexplorerapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.idigitalstudios.cineexplorerapp.data.local.entity.ShowEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ShowDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.showDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun makeEntity(id: Int, name: String = "Show $id", isFavorite: Boolean = false) =
        ShowEntity(id, name, "Summary", null, null, "Drama", "Running", "English", isFavorite)

    @Test
    fun insertAndRetrieveById() = runBlocking {
        val show = makeEntity(1)
        dao.upsertShow(show)
        assertEquals(show, dao.getShowById(1))
    }

    @Test
    fun getShowById_returnsNull_whenNotFound() = runBlocking {
        assertNull(dao.getShowById(999))
    }

    @Test
    fun upsertUpdatesExistingShow() = runBlocking {
        dao.upsertShow(makeEntity(1))
        dao.upsertShow(makeEntity(1, name = "Updated"))
        assertEquals("Updated", dao.getShowById(1)?.name)
    }

    @Test
    fun getFavoriteIds_returnsOnlyFavorites() = runBlocking {
        dao.upsertShows(listOf(makeEntity(1, isFavorite = true), makeEntity(2, isFavorite = false)))
        assertEquals(listOf(1), dao.getFavoriteIds())
    }

    @Test
    fun getFavoriteIds_returnsEmpty_whenNoFavorites() = runBlocking {
        dao.upsertShows(listOf(makeEntity(1), makeEntity(2)))
        assertTrue(dao.getFavoriteIds().isEmpty())
    }

    @Test
    fun updateFavorite_setsTrue() = runBlocking {
        dao.upsertShow(makeEntity(1, isFavorite = false))
        dao.updateFavorite(1, true)
        assertTrue(dao.getShowById(1)?.isFavorite ?: false)
    }

    @Test
    fun updateFavorite_setsFalse() = runBlocking {
        dao.upsertShow(makeEntity(1, isFavorite = true))
        dao.updateFavorite(1, false)
        assertFalse(dao.getShowById(1)?.isFavorite ?: true)
    }

    @Test
    fun getFavoritesFlow_emitsOnlyFavorites() = runBlocking {
        dao.upsertShows(listOf(makeEntity(1, isFavorite = true), makeEntity(2, isFavorite = false)))
        val favorites = dao.getFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals(1, favorites[0].id)
    }

    @Test
    fun favoritePreservedAcrossNetworkRefresh() = runBlocking {
        // Simulates what ShowRepositoryImpl does: read favorites, then upsert with flag
        dao.upsertShow(makeEntity(1, isFavorite = true))
        val favoriteIds = dao.getFavoriteIds().toSet()
        dao.upsertShow(makeEntity(1, isFavorite = favoriteIds.contains(1)))
        assertTrue(dao.getShowById(1)?.isFavorite ?: false)
    }
}
