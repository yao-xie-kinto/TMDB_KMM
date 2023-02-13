package com.yao.tmdb.domain.base

import com.yao.tmdb.data.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


abstract class UseCaseFlow<OUT> {
    operator fun <T> invoke(vararg params: SpecPair<T, () -> Spec<T>>): Flow<Response<OUT>> = try {
        build(*params).map { Response.Success(data = it) }
    } catch (ex: Exception) {
        flowOf(Response.Error(exception = ex))
    }

    protected abstract fun <T> build(vararg params: SpecPair<T, () -> Spec<T>>): Flow<OUT>
}

data class SpecPair<out A, out B>(val value: A, private val spec: B)

infix fun <A, B> A.spec(that: B): SpecPair<A, B> = SpecPair(this, that)

class Spec<out T>

// Example -> UseCase.invoke(language to ::stringSpec)
fun stringSpec() = Spec<String>()

// Example -> UseCase.invoke(99 to ::intSpec)
fun intSpec() = Spec<Int>()

// Example -> UseCase.invoke(99L to ::longSpec)
fun longSpec() = Spec<Long>()

// Example -> UseCase.invoke(99.0F to ::longSpec)
fun floatSpec() = Spec<Float>()

// Example -> UseCase.invoke(99.0 to ::longSpec)
fun doubleSpec() = Spec<Double>()