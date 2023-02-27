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
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = emptyList(),
    val id: Int,
    val media_type: String? = null,
    val original_language: String? = null,
    val overview: String,
    val popularity: Double? = 0.0,
    val poster_path: String? = null,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0,
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