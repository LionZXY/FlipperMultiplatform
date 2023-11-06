package com.flipperdevices.faphub.dao.network.ktorfit

import com.flipperdevices.faphub.dao.network.ktorfit.api.KtorfitApplicationApi
import com.flipperdevices.faphub.dao.network.ktorfit.api.KtorfitBundleApi
import com.flipperdevices.faphub.dao.network.ktorfit.api.KtorfitVersionApi
import com.flipperdevices.faphub.dao.network.ktorfit.utils.FapHubNetworkCategoryApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module


private const val FAP_URL = "https://catalog.flipperzero.one/api/"

fun koinKtorfit() = module {
    single<Ktorfit> {
        Ktorfit.Builder()
            .baseUrl(FAP_URL)
            .httpClient(get<HttpClient>())
            .build()
    }
    single<KtorfitApplicationApi> { get<Ktorfit>().create() }
    single<FapHubNetworkCategoryApi> { FapHubNetworkCategoryApi(get<Ktorfit>().create()) }
    single<KtorfitBundleApi> { get<Ktorfit>().create() }
    single<KtorfitVersionApi> { get<Ktorfit>().create() }
    single<HttpClient> {
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
