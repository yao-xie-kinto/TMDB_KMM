package com.yao.tmdb.sharedui

import com.yao.tmdb.domain.di.initKoin
import com.yao.tmdb.sharedui.di.applicationViewModels
import platform.Foundation.NSUserDefaults

fun initKoinIos(userDefaults: NSUserDefaults) = initKoin {
    modules(applicationViewModels)
}