package com.yao.tmdb.data.model

sealed class Response<out T> {
    class Success<T>(val data: T) : Response<T>()
    class Error(val exception: Exception) : Response<Nothing>()
}