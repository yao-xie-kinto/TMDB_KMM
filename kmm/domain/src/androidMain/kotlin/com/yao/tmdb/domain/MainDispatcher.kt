package com.yao.tmdb.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual class MainDispatcher {
    actual val dispatcher: CoroutineDispatcher = Dispatchers.Main
}

actual fun platformModule() = module {
    singleOf(::MainDispatcher)
    factory<CoroutineDispatcher> { Dispatchers.Default }
}