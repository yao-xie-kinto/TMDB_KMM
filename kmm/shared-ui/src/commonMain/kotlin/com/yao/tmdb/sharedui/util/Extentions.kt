package com.yao.tmdb.sharedui.util

const val ORIGINAL_PATH = "https://image.tmdb.org/t/p/original"
const val W500_PATH = "https://image.tmdb.org/t/p/w500"

fun String?.toOriginalImageUrl() = ORIGINAL_PATH.plus(this)

fun String?.toThumbnailImageUrl() = W500_PATH.plus(this)