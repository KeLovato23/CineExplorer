package com.idigitalstudios.cineexplorerapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetCastUseCase
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetEpisodesUseCase
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetShowDetailUseCase
import com.idigitalstudios.cineexplorerapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getCastUseCase: GetCastUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    private val showId: Int = checkNotNull(savedStateHandle["showId"])

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            val showDeferred = async { getShowDetailUseCase(showId) }
            val castDeferred = async { getCastUseCase(showId) }
            val episodesDeferred = async { getEpisodesUseCase(showId) }
            val showResult = showDeferred.await()
            val castResult = castDeferred.await()
            val episodesResult = episodesDeferred.await()
            showResult
                .onSuccess { show ->
                    _uiState.value = DetailUiState.Success(
                        show = show,
                        cast = castResult.getOrDefault(emptyList()),
                        episodes = episodesResult.getOrDefault(emptyList())
                    )
                }
                .onFailure { _uiState.value = DetailUiState.Error(it.message ?: "Error al cargar el detalle") }
        }
    }

    fun toggleFavorite() {
        val current = _uiState.value as? DetailUiState.Success ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(current.show)
            _uiState.value = current.copy(show = current.show.copy(isFavorite = !current.show.isFavorite))
        }
    }
}
