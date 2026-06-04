package com.idigitalstudios.cineexplorerapp.presentation.detail

import com.idigitalstudios.cineexplorerapp.domain.model.CastMember
import com.idigitalstudios.cineexplorerapp.domain.model.Episode
import com.idigitalstudios.cineexplorerapp.domain.model.Show

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val show: Show,
        val cast: List<CastMember> = emptyList(),
        val episodes: List<Episode> = emptyList()
    ) : DetailUiState
    data class Error(val message: String) : DetailUiState
}
