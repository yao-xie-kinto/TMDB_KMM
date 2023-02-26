package com.yao.tmdb.data.model

interface PagedResponse<T> {
    val page: Int
    val results: List<T>
    val total_pages: Int
    val total_results: Int
}
