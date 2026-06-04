package com.idigitalstudios.cineexplorerapp.domain.model

data class Show(
    val id: Int,
    val name: String,
    val summary: String,
    val imageUrl: String?,
    val rating: Double?,
    val genres: List<String>,
    val status: String,
    val language: String?,
    val isFavorite: Boolean = false
)
