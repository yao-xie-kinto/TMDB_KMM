package com.yao.tmdb.sharedui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
internal expect fun imageResource(id: String): ImageBitmap
