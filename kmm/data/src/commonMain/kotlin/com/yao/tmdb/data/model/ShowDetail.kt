package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShowDetail(
    val adult: Boolean = false,
    val backdrop_path: String? = "",
    val belongs_to_collection: BelongsToCollection? = null,
    val created_by: List<CreatedBy>? = emptyList(),
    val episode_run_time: List<Int>? = emptyList(),
    val first_air_date: String? = null,
    val budget: Int? = 0,
    val genres: List<Genre>? = emptyList(),
    val homepage: String? = "",
    val id: Int = 0,
    val in_production: Boolean? = null,
    val languages: List<String>? = emptyList(),
    val last_air_date: String? = null,
    val last_episode_to_air: LastEpisodeToAir? = null,
    val imdb_id: String? = null,
    val name: String? = null,
    val networks: List<Network>? = emptyList(),
    val next_episode_to_air: NextEpisodeToAir? = null,
    val number_of_episodes: Int? = null,
    val number_of_seasons: Int? = null,
    val origin_country: List<String>? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val original_name: String? = null,
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = "",
    val production_companies: List<ProductionCompany>? = null,
    val production_countries: List<ProductionCountry>? = null,
    val release_date: String? = "",
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val spoken_languages: List<SpokenLanguage>? = null,
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0,
    val seasons: List<Season>? = emptyList(),
    val type: String? = null
) {
    fun retrieveTitle(): String {
        return title?.ifEmpty { name ?: "" } ?: ""
    }
}