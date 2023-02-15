package com.yao.tmdb.data.repo

import com.yao.tmdb.data.Movie
import kotlinx.coroutines.flow.MutableStateFlow

private val fakeInitialNotes = mutableListOf(
    createMovie(1, "This is my 1st movie"),
    createMovie(2, "This is my 2nd movie"),
    createMovie(3, "This is my 3rd movie"),
    createMovie(4, "This is my 4th movie"),
    createMovie(5, "This is my 5th movie")
)

fun createMovie(id: Long, title: String) = Movie(
    id = id,
    adult = false,
    backdrop_path = "",
    genre_ids = emptyList(),
    original_language = "",
    original_title = "",
    overview = "",
    popularity = 0.1F,
    poster_path = "",
    release_date = "",
    title = title,
    video = true,
    vote_average = 5F,
    vote_count = 1000
)


object FakeRepository {
    private val watchers = hashMapOf<Long, MutableStateFlow<Movie>>()
    val items = MutableStateFlow(fakeInitialNotes)

    fun get(id: Long): Movie? {
        return items.value.firstOrNull { it.id == id }
    }

    fun update(note: Movie) {
        watchers[note.id]?.let {
            it.value = note
        }
        get(note.id)?.let { n ->
            items.value.let { list ->
                list[list.indexOf(n)] = note
                items.value = list
            }
        }
    }

    fun getLiveData(id: Long): MutableStateFlow<Movie> {
        return get(id)?.let {
            watchers.getOrPut(id) {
                MutableStateFlow(it)
            }
        } ?: throw IllegalArgumentException()
    }
}