package com.idigitalstudios.cineexplorerapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idigitalstudios.cineexplorerapp.connectivity.ConnectivityObserver
import com.idigitalstudios.cineexplorerapp.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getShowsUseCase: GetShowsUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val isConnected: StateFlow<Boolean> = connectivityObserver.isConnected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    init {
        observeCache()
        refresh()
    }

    private fun observeCache() {
        viewModelScope.launch {
            getShowsUseCase.getCached().collect { shows ->
                if (shows.isNotEmpty()) {
                    _uiState.value = HomeUiState.Success(shows)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val firstLoad = _uiState.value !is HomeUiState.Success
            if (firstLoad) {
                _uiState.value = HomeUiState.Loading
            } else {
                _isRefreshing.value = true
            }
            getShowsUseCase.refresh().onFailure { error ->
                if (_uiState.value is HomeUiState.Loading) {
                    _uiState.value = HomeUiState.Error(error.message ?: "Error al cargar las series")
                }
            }
            _isRefreshing.value = false
        }
    }
}
