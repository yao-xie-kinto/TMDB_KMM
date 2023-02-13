package com.yao.tmdb.sharedui.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(color = Color(0xFFFF8C00))
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun Loading_Preview() {
//    Loading()
//}