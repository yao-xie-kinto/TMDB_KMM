package com.yao.tmdb.sharedui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yao.tmdb.data.model.Genre
import com.yao.tmdb.data.model.ShowDetail
import com.yao.tmdb.data.repo.Repository
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import com.yao.tmdb.sharedui.component.ColumnSpacer
import com.yao.tmdb.sharedui.component.ExpandingText
import com.yao.tmdb.sharedui.component.RemoteImage
import com.yao.tmdb.sharedui.component.RowSpacer
import com.yao.tmdb.sharedui.theme.animateColor
import com.yao.tmdb.sharedui.theme.backgroundGradient
import com.yao.tmdb.sharedui.theme.textAnimateColor
import com.yao.tmdb.sharedui.util.toOriginalImageUrl
import com.yao.tmdb.sharedui.util.toThumbnailImageUrl
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import kotlin.math.roundToInt

private val HeaderHeight = 560.dp

@Composable
internal fun ShowDetailScreen(repository: Repository, id: Int) {
    val (state, channel) = rememberPresenter { Presenter(id, repository, it) }
    ShowDetailContent(
        PaddingValues(0.dp),
        state
//        {},
//        {}
    )
    channel.trySend(ShowDetailContract.Action.Init)
}

@Composable
internal fun ShowDetailContent(
    contentPadding: PaddingValues,
    state: ShowDetailContract.State,
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.fillMaxSize()
            .animateContentSize(),
    ) {
        item {
            HeaderContent(state.data)
        }
        item {
            BodyContent(state.data)
        }
    }
}

@Composable
private fun HeaderContent(data: ShowDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .clipToBounds()
            .offset {
                IntOffset(
                    x = 0,
                    y = 0
                )
            }
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(spring(stiffness = Spring.DampingRatioHighBouncy)),
            exit = fadeOut()
        ) {
            RemoteImage(
                imageUrl = data?.poster_path?.toOriginalImageUrl()
                    ?: data?.backdrop_path.toOriginalImageUrl(),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(animateColor())
        )
        Body(data)
    }
}

@Composable
private fun Body(data: ShowDetail) {
    val surfaceGradient = backgroundGradient().reversed()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .clipToBounds()
            .background(Brush.verticalGradient(surfaceGradient))
            .padding(horizontal = 16.dp)
    ) {
        ColumnSpacer(16)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = data.retrieveTitle(),
                style = MaterialTheme.typography.h3,
                fontWeight = W700,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )

            ColumnSpacer(8)

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                ExpandingText(
                    text = data.overview,
                )
            }

            ColumnSpacer(8)

            TvShowMetadata(data)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
internal fun TvShowMetadata(data: ShowDetail) {
    val divider = buildAnnotatedString {
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            color = MaterialTheme.colors.secondary
        )
        withStyle(tagStyle) {
            append("  •  ")
        }
    }
    val text = buildAnnotatedString {
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            color = MaterialTheme.colors.secondary,
            fontWeight = W700,
            background = MaterialTheme.colors.secondary.copy(alpha = 0.16f)
        )

        AnimatedVisibility(visible = !data.status.isNullOrBlank()) {
            data.status?.let {
                withStyle(tagStyle) {
                    append(" ")
                    append(it)
                    append(" ")
                }
                append(divider)
            }
        }
        data?.release_date?.let {
            append(it)
        }

        AnimatedVisibility(visible = !(data.seasons?.isNotEmpty() ?: true)) {
            data.seasons?.let {
                for (season in it) {
                    append(divider)
                    append(season.name)
                }
            }
        }

        append(divider)
        AnimatedVisibility(visible = !data.languages.isNullOrEmpty()) {
            data.languages?.let { language ->
                append(language.toString())
                append(divider)
            }
        }
        data?.vote_average?.let {
            val voteScore = (it * 100.0).roundToInt() / 100.0
            append("$voteScore")
        }
    }

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth()
        )
    }

    ColumnSpacer(8)

    data?.genres?.let {
        GenreText(it)
    }

    ColumnSpacer(8)
}

@Composable
private fun GenreText(genres: List<Genre>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(genres) { genre ->
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.onBackground,
                        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.2f)
                    ),
                    onClick = {}
                ) {
                    Text(
                        text = genre.name,
                        color = textAnimateColor(),
                        style = MaterialTheme.typography.body2,
                        fontWeight = W500
                    )
                }
            }
            RowSpacer(6)
        }
    }
}

@Composable
private fun BodyContent(data: ShowDetail) {
    data?.production_companies?.let { companies ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Divider(color = Color.Gray, thickness = 1.dp)
            ColumnSpacer(8)
            Text(
                text = "Production Companies",
                color = Color.Gray,
                style = MaterialTheme.typography.body1,
                fontWeight = W300
            )
            ColumnSpacer(8)
            companies.forEach { company ->
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    var companyName = company?.name ?: ""
                    if (!companyName.isNullOrEmpty()) {
                        companyName = "< ".plus(companyName).plus(" >")
                    }
                    Text(
                        text = companyName,
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        fontStyle = Italic,
                        fontWeight = W500
                    )
                    ColumnSpacer(8)
                    if (!company.logo_path.isNullOrEmpty()) {
                        RemoteImage(
                            imageUrl = company.logo_path.toThumbnailImageUrl(),
                            contentDescription = "",
                            modifier = Modifier.clip(MaterialTheme.shapes.medium)
                                .background(Color.White).padding(8.dp)
                        )
                    }
                }
                ColumnSpacer(16)
            }
        }
    }
}


@Composable
private fun Presenter(
    id: Int,
    repository: Repository,
    action: Flow<ShowDetailContract.Action>,
): ShowDetailContract.State {
    var data: ShowDetail by remember { mutableStateOf(ShowDetail()) }

    action.collectAction {
        when (this) {
            ShowDetailContract.Action.Init -> {
                data = repository.getMovieDetail(id)
            }
            else -> {}
        }
    }

    return ShowDetailContract.State(data = data)
}

interface ShowDetailContract {
    sealed interface Action : BaseAction {
        object Init : Action
    }

    data class State(
        val data: ShowDetail,
    ) : BaseState()

}
