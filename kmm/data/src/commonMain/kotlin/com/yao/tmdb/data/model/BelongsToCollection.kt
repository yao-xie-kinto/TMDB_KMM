package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BelongsToCollection(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val poster_path: String
)