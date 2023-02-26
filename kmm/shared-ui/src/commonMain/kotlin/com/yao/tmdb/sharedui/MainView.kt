package com.yao.tmdb.sharedui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

internal val darkModeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkModeState }

@Composable
internal fun MainView(viewModel: ApplicationViewModel) {
    Theme {
        MainComposeView(viewModel)
    }
    // isSystemInDarkTheme is not working correctly on iOS
    val darkMode = isSystemInDarkTheme()
    LaunchedEffect(key1 = Unit, block = {
        darkModeState.value = darkMode
    })
}
