package com.yao.tmdb.data.repo

import com.yao.tmdb.data.SharedConfig
import com.yao.tmdb.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

interface Repository {
    //Trending
    suspend fun getTrendingMovies(): List<Show>
    suspend fun getTrendingDramas(): List<Show>
    suspend fun getTrendingArtists(): List<Person>

    //Movie
    suspend fun getMovieDetail(id: Int): ShowDetail
    suspend fun getTopRatedMovies(): List<Show>
    suspend fun getPopularMovies(): List<Show>
    suspend fun getUpcomingMovies(): List<Show>
    suspend fun searchMovies(keyword: String): List<Show>

    //TV
    suspend fun getDramaDetail(id: Int): ShowDetail
    suspend fun getPopularDramas(): List<Show>
}

class RepositoryImpl(private val httpClient: HttpClient) : Repository {
    private suspend fun getTrending(mediaType: String): HttpResponse {
        return httpClient.get(SharedConfig.TMDB_API_VERSION.plus("/trending/$mediaType/day"))
    }

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

    //Movie
    override suspend fun getMovieDetail(id: Int): ShowDetail {
        val result: ShowDetail =
            httpClient.get(SharedConfig.TMDB_API_VERSION.plus("/movie/$id")).body()
        return result
    }

    private suspend fun getShowList(mediaType: String, listType: String): HttpResponse {
        return httpClient.get(SharedConfig.TMDB_API_VERSION.plus("/$mediaType/$listType"))
    }

    override suspend fun getTopRatedMovies(): List<Show> {
        val results: ShowResponse = getShowList("movie", "top_rated").body()
        return results.results
    }

    override suspend fun getPopularMovies(): List<Show> {
        val results: ShowResponse = getShowList("movie", "popular").body()
        return results.results
    }

    override suspend fun getUpcomingMovies(): List<Show> {
        val results: ShowResponse = getShowList("movie", "upcoming").body()
        return results.results
    }

    override suspend fun searchMovies(keyword: String): List<Show> {
        val results: ShowResponse =
            httpClient.get(SharedConfig.TMDB_API_VERSION.plus("/search/movie?query=$keyword"))
                .body()
        return results.results
    }

    //TV
    override suspend fun getDramaDetail(id: Int): ShowDetail {
        val result: ShowDetail =
            httpClient.get(SharedConfig.TMDB_API_VERSION.plus("/tv/$id")).body()
        return result
    }

    override suspend fun getPopularDramas(): List<Show> {
        val results: ShowResponse = getShowList("tv", "popular").body()
        return results.results
    }
}