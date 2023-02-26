package com.yao.tmdb.data.repo

import com.yao.tmdb.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Repository to provide a "Hello" data
 */

interface HomeRepository {
    suspend fun getTrendingMovies(): List<Show>
    suspend fun getTrendingDramas(): List<Show>
    suspend fun getTrendingPeople(): List<Person>
}

class HomeRepositoryImpl(private val httpClient: HttpClient) : HomeRepository {

    private val movies = arrayListOf<Show>()
    private val dramas = arrayListOf<Show>()
    private val artists = arrayListOf<Person>()

    override suspend fun getTrendingMovies(): List<Show> {
        val results: ShowResponse = getTrending("movie").body()
        return results.results
    }

    override suspend fun getTrendingDramas(): List<Show> {
        val results: ShowResponse = getTrending("tv").body()
        return results.results
    }

    override suspend fun getTrendingPeople(): List<Person> {
        val results: PersonResponse = getTrending("person").body()
        return results.results
    }

    private suspend fun getTrending(mediaType: String): HttpResponse {
        return httpClient.get("/trending/$mediaType/day")
    }

}