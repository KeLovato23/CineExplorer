package com.idigitalstudios.cineexplorerapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class ShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val summary: String,
    val imageUrl: String?,
    val rating: Double?,
    val genres: String,
    val status: String,
    val language: String?,
    val isFavorite: Boolean = false
)
