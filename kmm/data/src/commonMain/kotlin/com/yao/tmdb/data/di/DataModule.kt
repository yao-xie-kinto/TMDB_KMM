package com.yao.tmdb.data.di

import com.yao.tmdb.data.platform.getKtorEngine
import com.yao.tmdb.data.repo.MovieRepository
import com.yao.tmdb.data.repo.MovieRepositoryImpl
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun dataModule() = module {
    single<HttpClient> {
        HttpClient(getKtorEngine()) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
//                filter { request ->
//                    request.url.host.contains("ktor.io")
//                }
            }
            install(ContentNegotiation) {
            }
            install(HttpTimeout) {
                val timeout = 1000L * 60
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
        }
//            .apply {
//                plugin(HttpSend).intercept { request ->
//                    Napier.v("intercepted", null, "KtorClient")
//                    Napier.v(request.body.toString(), null, "KtorClient")
//                    execute(request)
//                }
//                // TODO: addInterceptor(UnifiedHeaderInterceptor())
//            }
    }
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
}

//private val repositoryModule = module {
//    single<Json> { Json { ignoreUnknownKeys = true } }
//    single<JsonResourceReader> { JsonResourceReader(get(), get()) }
//    single<ILocalDataSource> { LocalDataSource(get(), get(), Dispatchers.Default) }
//    single<IRemoteDataSource> { RemoteDataSource(get(), get()) }
//    single<IRepository> { Repository(get(), get()) }
//    single<HttpClient> {
//        HttpClient(getKtorEngine()) {
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        Napier.v(message, null, "KtorClient")
//                    }
//                }
//                level = LogLevel.ALL
//            }
//            install(ContentNegotiation) {
//                json()
//            }
//            install(HttpTimeout) {
//                val timeout = 1000L * 60
//                connectTimeoutMillis = timeout
//                requestTimeoutMillis = timeout
//                socketTimeoutMillis = timeout
//            }
//        }.apply {
//            plugin(HttpSend).intercept { request ->
//                Napier.v("intercepted", null, "KtorClient")
//                Napier.v(request.body.toString(), null, "KtorClient")
//                execute(request)
//            }
//            // TODO: addInterceptor(UnifiedHeaderInterceptor())
//        }
//    }
//    single<ApolloClient> {
//        ApolloClient.Builder()
//            .serverUrl(CommonConfig.BASE_URL + "/api/graphql")
//            .addHttpInterceptor(UnifiedHeaderInterceptor())
//            .addHttpInterceptor(LoggingInterceptor(level = LoggingInterceptor.Level.BODY, log = {
//                Napier.v(it, null, "ApolloClient")
//            }))
//            .build()
//    }
//}