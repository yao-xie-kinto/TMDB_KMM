package com.yao.tmdb.android

import android.graphics.Color
import android.os.Bundle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yao.tmdb.sharedui.Android
import com.yao.tmdb.sharedui.RootView
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // has to be set in code or in theme
        window.decorView.setBackgroundColor(Color.WHITE)
        window.statusBarColor = Color.parseColor("#cc7000")
        Android.context = this

//        setContent {
//            MyApplicationTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    GreetingView("Hello, Android!")
//                }
//            }
//        }


        setContent {
            RootView()
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
