package com.yao.tmdb.sharedui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yao.tmdb.data.model.Show
import com.yao.tmdb.data.repo.Repository
import com.yao.tmdb.sharedui.FullScreen
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import com.yao.tmdb.sharedui.component.RemoteImage
import com.yao.tmdb.sharedui.component.RowSpacer
import com.yao.tmdb.sharedui.theme.textAnimateColor
import com.yao.tmdb.sharedui.util.toThumbnailImageUrl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator

@Composable
internal fun HomeScreen(rootNavigator: Navigator, repository: Repository) {
    val (state, channel) = rememberPresenter { Presenter(repository, it) }
    HomeScrollingContent(
        PaddingValues(0.dp),
        state,
        { rootNavigator.navigate(route = "/${FullScreen.ShowDetail}/$it") },
        { rootNavigator.navigate(route = "/${FullScreen.ShowMore}/$it") }
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
            ShowsRow("Trending", state.trendingMovies, onClickShowDetail, onClickShowMore)
        }
        item {
            ShowsRow("Top-rated", state.topRatedMovies, onClickShowDetail, onClickShowMore)
        }
        item {
            ShowsRow("Popular", state.popularMovies, onClickShowDetail, onClickShowMore)
        }
        item {
            ShowsRow("Upcoming", state.upcomingMovies, onClickShowDetail, onClickShowMore)
        }
    }
}

@Composable
internal fun ShowsRow(
    showType: String,
    shows: List<Show>,
    onClickShowDetail: (showId: Int) -> Unit,
    onClickShowMore: (showType: String) -> Unit
) {
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
        BottomRow("$showType", onClickShowMore)
    }
}

@Composable
internal fun BottomRow(type: String, onClickShowMore: (showType: String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .fillMaxWidth(),
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = type,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.W700
        )
        Spacer(Modifier.weight(1f))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.2f)
                ),
                onClick = {
                    onClickShowMore.invoke(type)
                }
            ) {
                Text(
                    text = "+ More",
                    color = textAnimateColor(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W400
                )
            }
        }
    }
}

@Composable
private fun Presenter(
    repository: Repository,
    action: Flow<HomeContract.Action>,
): HomeContract.State {
    var trendingMovies: List<Show> by remember { mutableStateOf(emptyList()) }
    var topRatedMovies: List<Show> by remember { mutableStateOf(emptyList()) }
    var popularMovies: List<Show> by remember { mutableStateOf(emptyList()) }
    var upcomingMovies: List<Show> by remember { mutableStateOf(emptyList()) }

    action.collectAction {
        when (this) {
            is HomeContract.Action.Init -> {
                trendingMovies = repository.getTrendingMovies()
                topRatedMovies = repository.getTopRatedMovies()
                popularMovies = repository.getPopularMovies()
                upcomingMovies = repository.getUpcomingMovies()
            }
            else -> {}
        }
    }

    return HomeContract.State(
        trendingMovies = trendingMovies,
        topRatedMovies = topRatedMovies,
        popularMovies = popularMovies,
        upcomingMovies = upcomingMovies
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
        val upcomingMovies: List<Show>
    ) : BaseState()

}