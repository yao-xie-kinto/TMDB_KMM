package com.yao.tmdb.sharedui

import com.yao.tmdb.domain.di.initKoin
import com.yao.tmdb.sharedui.di.applicationViewModels
import org.koin.core.Koin
import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults

fun initKoinIos(userDefaults: NSUserDefaults) = initKoin {
    modules(applicationViewModels)
}

data class BundleProvider(val bundle: NSBundle)

val Koin.applicationViewModel: ApplicationViewModel
    get() = get()