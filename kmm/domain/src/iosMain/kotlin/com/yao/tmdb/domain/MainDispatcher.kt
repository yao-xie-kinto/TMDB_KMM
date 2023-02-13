package com.yao.tmdb.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import kotlin.coroutines.CoroutineContext

actual class MainDispatcher {
    actual val dispatcher: CoroutineDispatcher = MainLoopDispatcher
}

object MainLoopDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop().performBlock { block.run() }
    }
}

actual fun platformModule() = module {
    singleOf(::MainDispatcher)
    single<CoroutineDispatcher> { Dispatchers.Default }
}