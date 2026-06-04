package com.idigitalstudios.cineexplorerapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CastMemberDto(
    @SerializedName("person") val person: PersonDto,
    @SerializedName("character") val character: CharacterDto
)

data class PersonDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: ImageDto?
)

data class CharacterDto(
    @SerializedName("name") val name: String
)
