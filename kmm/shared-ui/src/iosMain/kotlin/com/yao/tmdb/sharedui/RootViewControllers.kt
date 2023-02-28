package com.yao.tmdb.sharedui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.yao.tmdb.sharedui.viewmodel.ApplicationViewModel
import moe.tlaster.precompose.PreComposeApplication
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme
import platform.CoreGraphics.CGFloat

fun getRootViewController(viewModel: ApplicationViewModel) = PreComposeApplication(title = "") {
    MainView(viewModel)
}

fun setSafeArea(start: CGFloat, top: CGFloat, end: CGFloat, bottom: CGFloat) {
    safeAreaState.value = PaddingValues(start.dp, top.dp, end.dp, bottom.dp)
}

fun setDarkMode() {
    darkModeState.value = currentSystemTheme == SystemTheme.DARK
}
