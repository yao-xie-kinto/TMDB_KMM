package com.yao.tmdb.sharedui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter

@Composable
internal fun HomeScreen() {

    val (state, channel) = rememberPresenter { Presenter(it) }

    Box(modifier = Modifier.background(Color.Red)) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = state.count)
            Button(onClick = {
                val result = channel.trySend(HomeContract.Action.Increment)
            }) {
                Text(text = "Increment")
            }
            Button(onClick = {
                val result = channel.trySend(HomeContract.Action.Decrement)
            }) {
                Text(text = "Decrement")
            }

        }
    }
}

@Composable
internal fun Presenter(
    action: Flow<HomeContract.Action>,
): HomeContract.State {
    var count by remember { mutableStateOf(0) }

    action.collectAction {
        when (this) {
            HomeContract.Action.Increment -> {
                delay(1000L * 3)
                count++
            }
            HomeContract.Action.Decrement -> count--
        }
    }
    return HomeContract.State(count = "Clicked $count times")
}

interface HomeContract {

    sealed interface Action : BaseAction {
        object Increment : Action
        object Decrement : Action
    }

    data class State(
        val count: String
    ) : BaseState()

}