package com.yao.tmdb.domain.base

import kotlin.native.concurrent.ThreadLocal

sealed class BasicUiState<out T> {
    data class Success<T>(open val data: T) : BasicUiState<T>()
    data class Error(val message: String? = null) : BasicUiState<Nothing>()

    @ThreadLocal
    object Loading : BasicUiState<Nothing>()

    @ThreadLocal
    object Empty : BasicUiState<Nothing>()

    @ThreadLocal
    object Idle : BasicUiState<Nothing>()

    // To solve the error: 'Sealed classes cannot be instantiated'
    // Swift error 'sealed class 'init()' is unavailable'
    open class Init<T> : BasicUiState<T>()
}

// Call this in Swift
class InitUiState<T> : BasicUiState.Init<T>()