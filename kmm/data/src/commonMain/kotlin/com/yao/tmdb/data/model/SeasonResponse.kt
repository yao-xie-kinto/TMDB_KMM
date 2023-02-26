package com.yao.tmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonResponse(
    @SerialName("air_date") val airDate: String,
    @SerialName("episodes") val episodes: List<EpisodesResponse>,
    @SerialName("name") val name: String,
    @SerialName("overview") val overview: String,
    @SerialName("id") val id: Int,
    @SerialName("season_number") val seasonNumber: Int
)
