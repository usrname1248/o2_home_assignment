package com.jozeftvrdy.networking.ktor

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val retryInterval = 5000L
private const val connectTimeoutInterval = 15000L
private const val socketTimeoutInterval = 15000L

fun initKtorHttpClient() : HttpClient = HttpClient(Android) {

    install(ContentNegotiation) {
        register(
            ContentType.Text.Html, KotlinxSerializationConverter(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        )

        json( Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }


    install(HttpRequestRetry) {
        val maxRetries = 3
        retryOnServerErrors(maxRetries = maxRetries)
        retryOnException(maxRetries = maxRetries)

        delayMillis { retry: Int ->
            retry * retryInterval
        }

    }

    install(HttpTimeout) {
        connectTimeoutMillis = connectTimeoutInterval
        socketTimeoutMillis = socketTimeoutInterval
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d(null, message)
            }
        }
        level = LogLevel.ALL
    }

    followRedirects = false
}