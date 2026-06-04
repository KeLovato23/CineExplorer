package com.idigitalstudios.cineexplorerapp.presentation.home

import com.idigitalstudios.cineexplorerapp.domain.model.Show

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Empty : HomeUiState
    data class Success(val shows: List<Show>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
