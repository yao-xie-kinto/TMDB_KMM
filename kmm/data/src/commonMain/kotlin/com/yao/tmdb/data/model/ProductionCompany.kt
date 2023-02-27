package com.yao.tmdb.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Int? = 0,
    val logo_path: String? = null,
    val name: String? = null,
    val origin_country: String? = null
)