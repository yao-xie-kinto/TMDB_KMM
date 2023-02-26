package com.yao.tmdb.sharedui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yao.tmdb.data.model.Show
import com.yao.tmdb.data.repo.HomeRepository
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import com.yao.tmdb.sharedui.component.RemoteImage
import com.yao.tmdb.sharedui.component.RowSpacer
import com.yao.tmdb.sharedui.util.toImageUrl
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter

@Composable
internal fun HomeScreen(repository: HomeRepository) {

    val (state, channel) = rememberPresenter { Presenter(repository, it) }

    Box(modifier = Modifier.background(Color.Red)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowList(state.movies)
            ShowList(state.dramas)
        }
    }

    channel.trySend(HomeContract.Action.Init)
}

@Composable
internal fun ShowList(shows: List<Show>) {
    LazyRow {
        itemsIndexed(shows) { index, show ->
            ShowCard(
                posterImageUrl = show.poster_path.toImageUrl(),
                title = show.retrieveTitle(),
                isFirstCard = index == 0,
//                onClick = { onItemClicked(tvShow.traktId) }
            )
        }
    }
}


@Composable
internal fun Presenter(
    repository: HomeRepository,
    action: Flow<HomeContract.Action>,
): HomeContract.State {
    var movies: List<Show> by remember { mutableStateOf(emptyList()) }
    var dramas: List<Show> by remember { mutableStateOf(emptyList()) }

    action.collectAction {
        when (this) {
            is HomeContract.Action.Init -> {
                movies = repository.getTrendingMovies()
                dramas = repository.getTrendingDramas()
            }
            else -> {}
        }
    }

    return HomeContract.State(movies = movies, dramas = dramas)
}

@Composable
internal fun ShowCard(
    modifier: Modifier = Modifier,
    posterImageUrl: String?,
    title: String,
    isFirstCard: Boolean = false,
    imageWidth: Dp = 120.dp,
    rowSpacer: Int = 4,
    onClick: () -> Unit = {}
) {
    RowSpacer(value = if (isFirstCard) 16 else 4)

    Column(
        modifier = modifier
            .width(imageWidth)
            .padding(vertical = 8.dp)
    ) {
        Card(
            elevation = 4.dp,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .clickable { onClick() }

        ) {
            Box(
                modifier = Modifier.clickable(onClick = onClick)
            ) {

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.Center)
                    )
                }

                RemoteImage(
                    imageUrl = posterImageUrl ?: "",
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(2 / 3f)
                        .clip(MaterialTheme.shapes.medium),
                )
            }
        }

        Spacer(Modifier.height(8.dp))
    }

    RowSpacer(value = rowSpacer)
}


interface HomeContract {

    sealed interface Action : BaseAction {
        object Init : Action
        object Increment : Action
        object Decrement : Action
    }

    data class State(
        val movies: List<Show>,
        val dramas: List<Show>
//        val artists: List<Person>
    ) : BaseState()

}