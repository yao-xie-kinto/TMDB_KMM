package com.yao.tmdb.feature.di

import com.yao.tmdb.feature.DiscoverMovieViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureModule() = module {
    factoryOf(::DiscoverMovieViewModel)
}
