package com.yao.tmdb.sharedui.feature

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yao.tmdb.data.model.Show
import com.yao.tmdb.data.repo.Repository
import com.yao.tmdb.sharedui.FullScreen
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import com.yao.tmdb.sharedui.component.RemoteImage
import com.yao.tmdb.sharedui.component.RowSpacer
import com.yao.tmdb.sharedui.util.toThumbnailImageUrl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator

@Composable
internal fun HomeScreen(navigator: Navigator, repository: Repository) {
    val (state, channel) = rememberPresenter { Presenter(repository, it) }
    HomeScrollingContent(
        PaddingValues(0.dp), state,
        {
            Napier.d {
                "navigator = $navigator"
            }
            navigator.navigate(route = FullScreen.ShowDetail.toString())
        },
        {}
    )
    channel.trySend(HomeContract.Action.Init)
}

@Composable
internal fun HomeScrollingContent(
    contentPadding: PaddingValues,
    state: HomeContract.State,
    onClickShowDetail: (showId: Int) -> Unit,
    onClickShowMore: (showType: String) -> Unit
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.fillMaxSize()
            .animateContentSize(),
    ) {
        item {
            TrendingsRow(state.trendingMovies)
        }
        item {
            ShowsRow(state.topRatedMovies, onClickShowDetail)
        }
        item {
            ShowsRow(state.popularMovies, onClickShowDetail)
        }
        item {
            ShowsRow(state.popularDramas, onClickShowDetail)
        }
    }
}

@Composable
internal fun TrendingsRow(shows: List<Show>) {
    Column {
        LazyRow {
            itemsIndexed(shows) { index, show ->
                ShowCard(
                    posterImageUrl = show.poster_path.toThumbnailImageUrl(),
                    title = show.retrieveTitle(),
                    isFirstCard = index == 0,
//                onClick = { onItemClicked(tvShow.traktId) }
                )
            }
        }
        Text("More")
    }
}

@Composable
internal fun ShowsRow(shows: List<Show>, onClickShowDetail: (showId: Int) -> Unit) {
    Column {
        LazyRow {
            itemsIndexed(shows) { index, show ->
                ShowCard(
                    posterImageUrl = show.poster_path.toThumbnailImageUrl(),
                    title = show.retrieveTitle(),
                    isFirstCard = index == 0,
                    onCardClick = { onClickShowDetail(show.id) }
                )
            }
        }
        Text("More")
    }
}

@Composable
internal fun Presenter(
    repository: Repository,
    action: Flow<HomeContract.Action>,
): HomeContract.State {
    var trendingMovies: List<Show> by remember { mutableStateOf(emptyList()) }
    var topRatedMovies: List<Show> by remember { mutableStateOf(emptyList()) }
    var popularMovies: List<Show> by remember { mutableStateOf(emptyList()) }
    var popularDramas: List<Show> by remember { mutableStateOf(emptyList()) }

    action.collectAction {
        when (this) {
            is HomeContract.Action.Init -> {
                trendingMovies = repository.getTrendingMovies()
                topRatedMovies = repository.getTopRatedMovies()
                popularMovies = repository.getPopularMovies()
                popularDramas = repository.getPopularDramas()
            }
            else -> {}
        }
    }

    return HomeContract.State(
        trendingMovies = trendingMovies,
        topRatedMovies = topRatedMovies,
        popularMovies = popularMovies,
        popularDramas = popularDramas
    )
}

@Composable
internal fun ShowCard(
    modifier: Modifier = Modifier,
    posterImageUrl: String?,
    title: String,
    isFirstCard: Boolean = false,
    imageWidth: Dp = 120.dp,
    rowSpacer: Int = 4,
    onCardClick: () -> Unit = {}
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
            modifier = Modifier.clickable { }
        ) {
            Box(
                modifier = Modifier.clickable { }
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
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            Napier.e { "Not working :(" }
                            onCardClick.invoke()
                        }
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
        val trendingMovies: List<Show>,
        val topRatedMovies: List<Show>,
        val popularMovies: List<Show>,
        val popularDramas: List<Show>
    ) : BaseState()

}