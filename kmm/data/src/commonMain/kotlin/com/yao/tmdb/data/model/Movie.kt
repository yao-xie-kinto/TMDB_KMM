package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    override val page: Int,
    override val results: List<Movie>,
    override val total_pages: Int,
    override val total_results: Int
) : PagedResponse<Movie>

@Serializable
data class Movie(
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
    val original_title: String,
    val release_date: String,
    val title: String,
    val video: Boolean
) : IShow {
    override fun getShowTitle(): String {
        return title
    }
}



