package com.yao.tmdb.sharedui.util

const val POSTER_PATH = "https://image.tmdb.org/t/p/original"

fun String?.toImageUrl() = POSTER_PATH.plus(this)