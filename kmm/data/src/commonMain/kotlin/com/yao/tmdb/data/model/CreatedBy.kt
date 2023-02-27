package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profile_path: String
)