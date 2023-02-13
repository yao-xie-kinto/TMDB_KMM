package com.yao.tmdb.android

import android.app.Application
import com.yao.tmdb.data.di.dataModule
import com.yao.tmdb.domain.di.domainModule
import com.yao.tmdb.domain.di.initKoin
import com.yao.tmdb.domain.platformModule
import com.yao.tmdb.feature.di.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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
            modules( featureModule())
        }
    }
}
