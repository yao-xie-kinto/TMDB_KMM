package com.yao.tmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodesResponse(
    @SerialName("air_date") val airDate: String,
    @SerialName("episode_number") val episodeNumber: Int,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("overview") val overview: String,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("still_path") val stillPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)
