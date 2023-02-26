package com.yao.tmdb.data.repo

import com.yao.tmdb.data.SharedConfig
import com.yao.tmdb.data.model.Person
import com.yao.tmdb.data.model.PersonResponse
import com.yao.tmdb.data.model.Show
import com.yao.tmdb.data.model.ShowResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Repository to provide a "Hello" data
 */

interface TrendingRepository {
    suspend fun getTrendingMovies(): List<Show>
    suspend fun getTrendingDramas(): List<Show>
    suspend fun getTrendingArtists(): List<Person>
}

class TrendingRepositoryImpl(private val httpClient: HttpClient) : TrendingRepository {

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

    override suspend fun getTrendingArtists(): List<Person> {
        val results: PersonResponse = getTrending("person").body()
        return results.results
    }

    private suspend fun getTrending(mediaType: String): HttpResponse {
        return httpClient.get(SharedConfig.TMDB_API_VERSION.plus("/trending/$mediaType/day"))
    }

}