package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)