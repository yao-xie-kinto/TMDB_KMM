package com.yao.tmdb.sharedui.di

import com.yao.tmdb.sharedui.viewmodel.ApplicationViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationViewModels: Module = module {
    single { ApplicationViewModel(get()) }
}