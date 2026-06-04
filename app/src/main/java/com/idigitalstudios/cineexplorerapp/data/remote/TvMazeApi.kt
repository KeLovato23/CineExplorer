package com.idigitalstudios.cineexplorerapp.data.remote

import com.idigitalstudios.cineexplorerapp.data.remote.dto.CastMemberDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.EpisodeDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.ShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApi {
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int = 0): List<ShowDto>

    @GET("shows/{id}")
    suspend fun getShowDetail(@Path("id") id: Int): ShowDto

    @GET("shows/{id}/cast")
    suspend fun getCast(@Path("id") id: Int): List<CastMemberDto>

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") id: Int): List<EpisodeDto>
}
