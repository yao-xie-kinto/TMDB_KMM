package com.yao.tmdb.data.platform

import io.ktor.client.engine.*

expect fun getKtorEngine(): HttpClientEngine