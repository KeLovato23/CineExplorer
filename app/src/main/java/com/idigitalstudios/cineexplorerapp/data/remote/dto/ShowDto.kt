package com.idigitalstudios.cineexplorerapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ShowDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: String?,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("status") val status: String,
    @SerializedName("rating") val rating: RatingDto?,
    @SerializedName("image") val image: ImageDto?,
    @SerializedName("summary") val summary: String?
)

data class RatingDto(
    @SerializedName("average") val average: Double?
)

data class ImageDto(
    @SerializedName("medium") val medium: String?,
    @SerializedName("original") val original: String?
)
