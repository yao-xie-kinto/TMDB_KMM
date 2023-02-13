package com.yao.tmdb.data.platform

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual fun getKtorEngine(): HttpClientEngine = Darwin.create()