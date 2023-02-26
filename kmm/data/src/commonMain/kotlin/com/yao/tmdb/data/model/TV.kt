package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TVResponse(
    override val page: Int,
    override val results: List<TV>,
    override val total_pages: Int,
    override val total_results: Int
) : PagedResponse<TV>

@Serializable
data class TV(
    override val adult: Boolean,
    override val backdrop_path: String,
    override val genre_ids: List<Int>,
    override val id: Int,
    override val media_type: String,
    override val original_language: String,
    override val overview: String,
    override val popularity: Double,
    override val poster_path: String,
    override val vote_average: Double,
    override val vote_count: Int,
    val first_air_date: String,
    val name: String,
    val origin_country: List<String>,
    val original_name: String
) : IShow {
    override fun getShowTitle(): String {
        return name
    }
}