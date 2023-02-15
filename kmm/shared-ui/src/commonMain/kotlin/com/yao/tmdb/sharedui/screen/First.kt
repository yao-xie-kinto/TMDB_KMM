package com.yao.tmdb.sharedui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yao.tmdb.feature.trending.TrendingViewModel
import moe.tlaster.precompose.ui.viewModel

@Composable
internal fun First() {

    val viewModel = viewModel(TrendingViewModel::class) {
        TrendingViewModel()
    }

    Box(modifier = Modifier.background(Color.Red)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("First!")
            LazyColumn {
                itemsIndexed(viewModel.items.value) { _, item ->
                    Text(item.title)
                }
            }
        }
    }
}