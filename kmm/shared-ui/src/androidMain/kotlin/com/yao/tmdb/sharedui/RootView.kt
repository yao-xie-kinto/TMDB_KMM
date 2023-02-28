package com.yao.tmdb.sharedui

import androidx.compose.runtime.Composable
import com.yao.tmdb.sharedui.viewmodel.ApplicationViewModel

@Composable
fun RootView(viewModel: ApplicationViewModel) {
    MainView(viewModel)
}
