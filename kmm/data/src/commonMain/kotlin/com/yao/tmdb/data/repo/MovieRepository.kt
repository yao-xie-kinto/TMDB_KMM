package com.yao.tmdb.data.repo

import com.yao.tmdb.data.Movie
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Repository to provide a "Hello" data
 */

interface MovieRepository {
    suspend fun findMovie(id: Long): Movie?
    suspend fun discoverMovies(): List<Movie>
}

class MovieRepositoryImpl(private val httpClient: HttpClient) : MovieRepository {

    private val movies = arrayListOf<Movie>()

    override suspend fun findMovie(id: Long): Movie? {
        return movies.firstOrNull { it.id == id }
    }

    override suspend fun discoverMovies(): List<Movie> {
        val response =
            httpClient.get("https://api.themoviedb.org/3/discover/movie?api_key=c75ef61f2c1d6b7339377cdc69bb028f&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate")
        val text = response.bodyAsText()
        return emptyList()
    }

}