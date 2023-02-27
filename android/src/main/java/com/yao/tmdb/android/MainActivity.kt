package com.yao.tmdb.android

import android.graphics.Color
import android.os.Bundle
import com.yao.tmdb.sharedui.Android
import com.yao.tmdb.sharedui.ApplicationViewModel
import com.yao.tmdb.sharedui.RootView
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import org.koin.android.ext.android.inject

class MainActivity : PreComposeActivity() {

    private val applicationViewModel: ApplicationViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.setBackgroundColor(Color.WHITE)
        window.statusBarColor = Color.DKGRAY
        Android.context = this
        setContent {
            RootView(applicationViewModel)
        }
    }
}