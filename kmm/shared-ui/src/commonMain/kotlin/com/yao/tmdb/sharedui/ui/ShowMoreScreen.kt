package com.yao.tmdb.sharedui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yao.tmdb.data.model.Show
import com.yao.tmdb.data.repo.Repository
import com.yao.tmdb.sharedui.FullScreen
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import com.yao.tmdb.sharedui.util.toThumbnailImageUrl
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator

@Composable
internal fun ShowMoreScreen(rootNavigator: Navigator, repository: Repository, type: String) {
    val (state, channel) = rememberPresenter { Presenter(type, repository, it) }
    ShowGrid(
        PaddingValues(0.dp),
        state.data,
    ) { rootNavigator.navigate(route = "/${FullScreen.ShowDetail}/$it") }
    channel.trySend(ShowMoreContract.Action.Init)
}

@Composable
internal fun ShowGrid(
    paddingValues: PaddingValues,
    shows: List<Show>,
    onClickShowDetail: (showId: Int) -> Unit
) {
    LazyVerticalGrid(
        contentPadding = paddingValues,
        modifier = Modifier.fillMaxSize()
            .animateContentSize(),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(shows.size) { index ->
            val show = shows.get(index)
            ShowCard(
                posterImageUrl = show.poster_path.toThumbnailImageUrl(),
                title = show.retrieveTitle(),
                isFirstCard = index == 0,
                onCardClick = { onClickShowDetail(show.id) }
            )
        }
    }
}


@Composable
private fun Presenter(
    type: String,
    repository: Repository,
    action: Flow<ShowMoreContract.Action>,
): ShowMoreContract.State {
    var data: List<Show> by remember { mutableStateOf(emptyList()) }

    action.collectAction {
        when (this) {
            ShowMoreContract.Action.Init -> {
                when (type) {
                    "Trending" -> {
                        data = repository.getTrendingMovies()
                    }
                    "Top-rated" -> {
                        data = repository.getTopRatedMovies()
                    }
                    "Popular" -> {
                        data = repository.getPopularMovies()
                    }
                    "Upcoming" -> {
                        data = repository.getUpcomingMovies()
                    }
                }
            }
            else -> {}
        }
    }

    return ShowMoreContract.State(data = data)
}

interface ShowMoreContract {
    sealed interface Action : BaseAction {
        object Init : Action
    }

    data class State(
        val data: List<Show>
    ) : BaseState()

}
