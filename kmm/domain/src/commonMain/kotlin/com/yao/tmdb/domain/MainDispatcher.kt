package com.yao.tmdb.domain

import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect class MainDispatcher() {
    val dispatcher: CoroutineDispatcher
}

expect fun platformModule(): Module