package com.idigitalstudios.cineexplorerapp.data.mapper

import android.text.Html
import com.idigitalstudios.cineexplorerapp.data.local.entity.ShowEntity
import com.idigitalstudios.cineexplorerapp.data.remote.dto.CastMemberDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.EpisodeDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.ShowDto
import com.idigitalstudios.cineexplorerapp.domain.model.CastMember
import com.idigitalstudios.cineexplorerapp.domain.model.Episode
import com.idigitalstudios.cineexplorerapp.domain.model.Show

fun ShowDto.toEntity(isFavorite: Boolean = false) = ShowEntity(
    id = id,
    name = name,
    summary = summary?.stripHtml() ?: "",
    imageUrl = image?.medium,
    rating = rating?.average,
    genres = genres.joinToString(","),
    status = status,
    language = language,
    isFavorite = isFavorite
)

fun ShowEntity.toDomain() = Show(
    id = id,
    name = name,
    summary = summary,
    imageUrl = imageUrl,
    rating = rating,
    genres = if (genres.isBlank()) emptyList() else genres.split(","),
    status = status,
    language = language,
    isFavorite = isFavorite
)

fun ShowDto.toDomain(isFavorite: Boolean = false) = Show(
    id = id,
    name = name,
    summary = summary?.stripHtml() ?: "",
    imageUrl = image?.medium,
    rating = rating?.average,
    genres = genres,
    status = status,
    language = language,
    isFavorite = isFavorite
)

fun CastMemberDto.toDomain() = CastMember(
    id = person.id,
    personName = person.name,
    characterName = character.name,
    imageUrl = person.image?.medium
)

fun EpisodeDto.toDomain() = Episode(
    id = id,
    name = name,
    season = season,
    number = number,
    imageUrl = image?.medium,
    summary = summary?.stripHtml()
)

private fun String.stripHtml(): String =
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString().trim()
