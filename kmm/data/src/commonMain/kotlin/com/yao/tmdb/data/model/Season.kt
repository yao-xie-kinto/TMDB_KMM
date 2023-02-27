package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Season(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
)