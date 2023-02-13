package com.yao.tmdb.domain.di

import com.yao.tmdb.domain.DiscoverMovieUseCase
import com.yao.tmdb.domain.MainDispatcher
import com.yao.tmdb.domain.platformModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun domainModule() = module {
//    singleOf(::MainDispatcher)
//    single { MainDispatcher() }
    factoryOf(::DiscoverMovieUseCase)
    platformModule()
}