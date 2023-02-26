package com.yao.tmdb.sharedui

import android.content.Context
import androidx.annotation.NonNull

class ContextUtil private constructor() {

    companion object {
        private var appContext: Context? = null

        fun init(@NonNull context: Context) {
            appContext = context.applicationContext
        }

        fun getContext(): Context? {
            if (appContext != null) {
                return appContext
            }
            throw NullPointerException("Application context is not initialized!")
        }
    }
}