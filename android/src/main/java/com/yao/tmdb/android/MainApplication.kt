package com.yao.tmdb.android

import android.app.Application
import com.yao.tmdb.domain.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@MainApplication)
//            androidLogger()
//            modules(dataModule() + domainModule() + featureModule() + platformModule())
//        }
        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
//            modules( featureModule())
        }
    }
}
