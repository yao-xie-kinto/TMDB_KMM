package com.yao.tmdb.sharedui.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yao.tmdb.domain.base.BasicUiState

@Composable
internal fun <T> ManagementResourceState(
    resourceState: BasicUiState<T>,
    successView: @Composable (data: T?) -> Unit,
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit,
    msgTryAgain: String = "No data to show",
    onCheckAgain: () -> Unit,
    msgCheckAgain: String = "An error has occurred"
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when (resourceState) {
            is BasicUiState.Success -> successView(resourceState.data)
            is BasicUiState.Error -> Error(onTryAgain = onTryAgain, msg = msgTryAgain)
            is BasicUiState.Empty -> Empty(onCheckAgain = onCheckAgain, msg = msgCheckAgain)
            BasicUiState.Loading -> Loading()
            BasicUiState.Idle -> Unit
            else -> {}
        }
    }
}