package com.idigitalstudios.cineexplorerapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.idigitalstudios.cineexplorerapp.domain.model.CastMember
import com.idigitalstudios.cineexplorerapp.domain.model.Episode
import com.idigitalstudios.cineexplorerapp.domain.model.Show
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetCastUseCase
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetEpisodesUseCase
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetShowDetailUseCase
import com.idigitalstudios.cineexplorerapp.domain.usecase.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val savedStateHandle = SavedStateHandle(mapOf("showId" to 1))
    private val getShowDetailUseCase = mockk<GetShowDetailUseCase>()
    private val toggleFavoriteUseCase = mockk<ToggleFavoriteUseCase>()
    private val getCastUseCase = mockk<GetCastUseCase>()
    private val getEpisodesUseCase = mockk<GetEpisodesUseCase>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun makeShow(isFavorite: Boolean = false) =
        Show(1, "Breaking Bad", "Summary", null, 9.5, listOf("Drama"), "Ended", "English", isFavorite)

    private fun buildViewModel() = DetailViewModel(
        savedStateHandle,
        getShowDetailUseCase,
        toggleFavoriteUseCase,
        getCastUseCase,
        getEpisodesUseCase
    )

    @Test
    fun `emits Success with show cast and episodes on load`() {
        val show = makeShow()
        val cast = listOf(CastMember(1, "Bryan Cranston", "Walter White", null))
        val episodes = listOf(Episode(1, "Pilot", 1, 1, null, null))

        coEvery { getShowDetailUseCase(1) } returns Result.success(show)
        coEvery { getCastUseCase(1) } returns Result.success(cast)
        coEvery { getEpisodesUseCase(1) } returns Result.success(episodes)

        val vm = buildViewModel()

        assertEquals(DetailUiState.Success(show, cast, episodes), vm.uiState.value)
    }

    @Test
    fun `emits Error when show detail fetch fails`() {
        coEvery { getShowDetailUseCase(1) } returns Result.failure(Exception("Not found"))
        coEvery { getCastUseCase(1) } returns Result.success(emptyList())
        coEvery { getEpisodesUseCase(1) } returns Result.success(emptyList())

        val vm = buildViewModel()

        assertTrue(vm.uiState.value is DetailUiState.Error)
    }

    @Test
    fun `cast is empty when cast fetch fails but show succeeds`() {
        val show = makeShow()
        coEvery { getShowDetailUseCase(1) } returns Result.success(show)
        coEvery { getCastUseCase(1) } returns Result.failure(Exception("Cast unavailable"))
        coEvery { getEpisodesUseCase(1) } returns Result.success(emptyList())

        val vm = buildViewModel()

        val state = vm.uiState.value as DetailUiState.Success
        assertEquals(show, state.show)
        assertTrue(state.cast.isEmpty())
    }

    @Test
    fun `episodes are empty when episodes fetch fails but show succeeds`() {
        val show = makeShow()
        coEvery { getShowDetailUseCase(1) } returns Result.success(show)
        coEvery { getCastUseCase(1) } returns Result.success(emptyList())
        coEvery { getEpisodesUseCase(1) } returns Result.failure(Exception("Episodes unavailable"))

        val vm = buildViewModel()

        val state = vm.uiState.value as DetailUiState.Success
        assertTrue(state.episodes.isEmpty())
    }

    @Test
    fun `toggleFavorite flips isFavorite from false to true`() {
        val show = makeShow(isFavorite = false)
        coEvery { getShowDetailUseCase(1) } returns Result.success(show)
        coEvery { getCastUseCase(1) } returns Result.success(emptyList())
        coEvery { getEpisodesUseCase(1) } returns Result.success(emptyList())
        coJustRun { toggleFavoriteUseCase(show) }

        val vm = buildViewModel()
        vm.toggleFavorite()

        val state = vm.uiState.value as DetailUiState.Success
        assertTrue(state.show.isFavorite)
    }
}
