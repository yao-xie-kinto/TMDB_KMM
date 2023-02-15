package com.yao.tmdb.sharedui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yao.tmdb.feature.DiscoverMovieContent
import com.yao.tmdb.feature.DiscoverMovieContract
import com.yao.tmdb.feature.DiscoverMovieViewModel
import com.yao.tmdb.sharedui.state.ManagementResourceState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun DiscoverMovieScreen(
    onBackPressed: () -> Unit,
    viewModel: DiscoverMovieViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = when (effect) {
                    is DiscoverMovieContract.Effect.Added -> "Faq list added"
                    DiscoverMovieContract.Effect.Removed -> "Faq lis removed"
                }
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(onBackPressed) },
    ) { padding ->
        ManagementResourceState(
            resourceState = state.uiState,
            successView = { faqList ->
                requireNotNull(faqList)

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    FaqView(faqList) {
                        viewModel.setEvent(DiscoverMovieContract.Event.Expand(it))
                    }
                }
            },
            modifier = Modifier.padding(padding),
            onTryAgain = { viewModel.setEvent(DiscoverMovieContract.Event.Retry) },
            onCheckAgain = { viewModel.setEvent(DiscoverMovieContract.Event.Retry) },
        )
    }
}

@Composable
internal fun TopBar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "FAQ",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 70.dp, 0.dp)

            )
        },
//        backgroundColor = Colors.white,
        navigationIcon = {
            IconButton(
                onClick = {
                    // Show Faqs from here
                    onBackPressed()
                },
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "back")
            }
        }
    )
}

@Composable
internal fun FaqView(faqList: List<DiscoverMovieContent>, onTap: (faq: DiscoverMovieContent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyColumn {
            itemsIndexed(faqList) { n, faq ->
                FaqItemView(
                    faq = faq,
                    isLastItem = n == faqList.lastIndex,
                    onClick = onTap
                )
            }
        }
    }
}

@Composable
internal fun FaqItemView(faq: DiscoverMovieContent, isLastItem: Boolean, onClick: (notice: DiscoverMovieContent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(faq)
            }
    ) {
        Text(
            text = faq.title,
            fontWeight = FontWeight.Bold
        )
    }
}