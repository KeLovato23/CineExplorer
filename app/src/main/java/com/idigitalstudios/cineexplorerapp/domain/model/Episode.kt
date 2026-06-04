package com.idigitalstudios.cineexplorerapp.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val imageUrl: String?,
    val summary: String?
)
