package com.yao.tmdb.domain.executor

import com.yao.tmdb.domain.MainDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

abstract class MainIoExecutor : IExecutorScope, CoroutineScope, KoinComponent {

    private val mainDispatcher: MainDispatcher by inject()
    private val ioDispatcher: CoroutineDispatcher by inject()

    private val job: CompletableJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job + mainDispatcher.dispatcher

    override fun cancel() {
        job.cancel()
    }

    protected fun <T> collect( // Rename to collect in case of clashes of CoroutineScope.launch
        flow: Flow<T>,
        callback: (T) -> Unit
    ) {
        launch {
            flow.flowOn(ioDispatcher)
                .collect {
                    callback(it)
                }
        }
    }

    // In Sequence
    protected fun <T1, T2> collect(
        flow1: Flow<T1>,
        callback1: (T1) -> Unit,
        flow2: Flow<T2>,
        callback2: (T2) -> Unit,
    ) {
        launch {
            flow1.flowOn(ioDispatcher)
                // Flattens the given flow of flows into a single flow in a sequential manner, without interleaving nested flows.
                .flatMapConcat {
                    callback1(it)
                    flow2
                }.flowOn(ioDispatcher)
                .collect {
                    callback2(it)
                }
        }
    }

}
