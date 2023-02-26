package com.yao.tmdb.android

import android.app.Application
import com.yao.tmdb.domain.di.initKoin
import com.yao.tmdb.sharedui.di.applicationViewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(applicationViewModels)
        }
    }
}
