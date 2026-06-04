package com.idigitalstudios.cineexplorerapp.presentation.home

import com.idigitalstudios.cineexplorerapp.connectivity.ConnectivityObserver
import com.idigitalstudios.cineexplorerapp.domain.model.Show
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetShowsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val getShowsUseCase = mockk<GetShowsUseCase>()
    private val connectivityObserver = mockk<ConnectivityObserver>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { connectivityObserver.isConnected } returns MutableStateFlow(true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun makeShow(id: Int) =
        Show(id, "Show $id", "", null, null, emptyList(), "Running", null, false)

    private fun buildViewModel() = HomeViewModel(getShowsUseCase, connectivityObserver)

    @Test
    fun `initial state is Loading when cache is empty`() {
        every { getShowsUseCase.getCached() } returns emptyFlow()
        coEvery { getShowsUseCase.refresh() } returns Result.success(Unit)

        val vm = buildViewModel()

        assertEquals(HomeUiState.Loading, vm.uiState.value)
    }

    @Test
    fun `state becomes Success when cache returns shows`() {
        val shows = listOf(makeShow(1), makeShow(2))
        every { getShowsUseCase.getCached() } returns flowOf(shows)
        coEvery { getShowsUseCase.refresh() } returns Result.success(Unit)

        val vm = buildViewModel()

        assertEquals(HomeUiState.Success(shows), vm.uiState.value)
    }

    @Test
    fun `state becomes Error when refresh fails and cache is empty`() {
        every { getShowsUseCase.getCached() } returns flowOf(emptyList())
        coEvery { getShowsUseCase.refresh() } returns Result.failure(Exception("Network error"))

        val vm = buildViewModel()

        assertTrue(vm.uiState.value is HomeUiState.Error)
    }

    @Test
    fun `isRefreshing is false after refresh completes`() {
        val shows = listOf(makeShow(1))
        every { getShowsUseCase.getCached() } returns flowOf(shows)
        coEvery { getShowsUseCase.refresh() } returns Result.success(Unit)

        val vm = buildViewModel()

        assertFalse(vm.isRefreshing.value)
    }

    @Test
    fun `refresh when already Success sets isRefreshing instead of Loading`() {
        val shows = listOf(makeShow(1))
        every { getShowsUseCase.getCached() } returns flowOf(shows)
        coEvery { getShowsUseCase.refresh() } returns Result.success(Unit)

        val vm = buildViewModel()
        // State is now Success — calling refresh should not revert to Loading
        vm.refresh()

        assertTrue(vm.uiState.value is HomeUiState.Success)
    }
}
