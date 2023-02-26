package com.yao.tmdb.data.model

import kotlinx.serialization.SerialName
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
    @SerialName("adult") val adult: Boolean,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String?,
    @SerialName("original_name") val originalName: String?,
    @SerialName("media_type") val mediaType: String?,
    @SerialName("popularity") val popularity: Double,
    @SerialName("gender") val gender: Int,
    @SerialName("known_for_department") val knownForDepartment: String?,
    @SerialName("profile_path") val profilePath: String?,
)
