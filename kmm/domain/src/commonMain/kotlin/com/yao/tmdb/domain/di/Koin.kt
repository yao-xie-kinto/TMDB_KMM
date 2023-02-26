package com.yao.tmdb.domain.di

import com.yao.tmdb.data.di.dataModule
import com.yao.tmdb.domain.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataModule(),
            domainModule(),
            platformModule()
        )
    }