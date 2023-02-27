package com.yao.tmdb.sharedui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun backgroundGradient(): List<Color> {
    return listOf(
        MaterialTheme.colors.surface,
        MaterialTheme.colors.surface.copy(alpha = 0.6F),
        MaterialTheme.colors.surface.copy(alpha = 0.5F),
        MaterialTheme.colors.surface.copy(alpha = 0.4F),
        Color.Transparent
    )
}
