package com.yao.tmdb.sharedui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun Theme(content: @Composable () -> Unit) {
    val colors = darkColors(primary = Color.Black, surface = Color.Black, onPrimary = Color.White)

    MaterialTheme(
        colors = colors
    ) {
        content()
    }
}
