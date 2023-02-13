package com.yao.tmdb.data.platform

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual fun getKtorEngine(): HttpClientEngine = OkHttp.create()
