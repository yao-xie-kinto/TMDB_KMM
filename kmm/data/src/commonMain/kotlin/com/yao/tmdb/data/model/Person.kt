package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(
    override val page: Int,
    override val results: List<Person>,
    override val total_pages: Int,
    override val total_results: Int,
) : PagedResponse<Person>

@Serializable
data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for: List<Show>,
    val known_for_department: String,
    val media_type: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)
