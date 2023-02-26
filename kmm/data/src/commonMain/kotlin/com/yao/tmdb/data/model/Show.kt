package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    override val page: Int,
    override val results: List<Show>,
    override val total_pages: Int,
    override val total_results: Int
) : PagedResponse<Show>


@Serializable
data class Show(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val original_language: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int,
    //Movie
    val original_title: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    //TV
    val first_air_date: String? = null,
    val name: String? = null,
    val origin_country: List<String>? = null,
    val original_name: String? = null
) {
    fun retrieveTitle(): String {
        return title?.ifEmpty { name ?: "" } ?: ""
    }
}