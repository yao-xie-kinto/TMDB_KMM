package com.yao.tmdb.sharedui

import androidx.compose.ui.graphics.ImageBitmap

expect fun ByteArray.toImageBitmap(): ImageBitmap
