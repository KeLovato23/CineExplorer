package com.idigitalstudios.cineexplorerapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("season") val season: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("image") val image: ImageDto?,
    @SerializedName("summary") val summary: String?
)
