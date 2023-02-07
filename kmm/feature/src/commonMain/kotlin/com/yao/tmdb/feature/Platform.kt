package com.yao.tmdb.feature

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform