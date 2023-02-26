package com.yao.tmdb.data

import kotlin.properties.Delegates

object SharedConfig {

    const val TMDB_DOMAIN: String = "api.themoviedb.org"
    const val TMDB_API_VERSION: String = "3"
    const val TMDB_API_KEY: String = "c75ef61f2c1d6b7339377cdc69bb028f"

    var DEBUG by Delegates.notNull<Boolean>()

    private val env: String by lazy {
        "dev"
    }

    init {
        DEBUG = when (env) {
            "prod" -> false
            else -> true
        }
    }
}