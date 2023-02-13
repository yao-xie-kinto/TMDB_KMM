package com.yao.tmdb.feature

import com.yao.tmdb.domain.base.BasicUiState
import com.yao.tmdb.domain.base.UiEffect
import com.yao.tmdb.domain.base.UiEvent
import com.yao.tmdb.domain.base.UiState
import kotlin.native.concurrent.ThreadLocal

interface DiscoverMovieContract {
    sealed class Event : UiEvent {
        data class DiscoverMovie(
            val sort_by: String
        ) : Event()

        data class Expand(
            val content: DiscoverMovieContent
        ) : Event()

        @ThreadLocal
        object Retry : Event()
    }

    data class State(
        val uiState: BasicUiState<List<DiscoverMovieContent>>,
        val isChanged: Boolean
    ) : UiState

    sealed class Effect : UiEffect {
        @ThreadLocal
        object Added : Effect()

        @ThreadLocal
        object Removed : Effect()
    }
}

data class DiscoverMovieContent(
    val title: String,
    val poster_path: String,
    val backdrop_path: String,
    var isFavourite: Boolean = false
) {
    /**
     * toggle isExpanded flag and return a new FaqContent object
     */
    fun toggleExpand(): DiscoverMovieContent {
        return DiscoverMovieContent(this.title, this.poster_path, this.backdrop_path, this.isFavourite)
    }
}