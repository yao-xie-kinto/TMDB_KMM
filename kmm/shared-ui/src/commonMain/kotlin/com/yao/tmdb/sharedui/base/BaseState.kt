package com.yao.tmdb.sharedui.base

open class BaseState(open val uiState: UiState = UiState.Init)

enum class UiState {
    Init, Loading, Success, Error, Empty, Idle;
}