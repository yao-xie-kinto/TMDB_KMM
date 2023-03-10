package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrailerResponse(
    val id: Int,
    val results: List<Trailer>
)

@Serializable
data class Trailer(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)