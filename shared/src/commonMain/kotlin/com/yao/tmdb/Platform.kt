package com.yao.tmdb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform