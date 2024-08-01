package com.jozeftvrdy.networking.di

import com.jozeftvrdy.networking.api.VersionApi
import com.jozeftvrdy.networking.ktor.KtorNetworkCallFactory
import com.jozeftvrdy.networking.ktor.initKtorHttpClient
import org.koin.dsl.module

val networkModule = module {
    single {
        initKtorHttpClient()
    }

    factory {
        KtorNetworkCallFactory(
            ktorHttpClient = get()
        )
    }

}

val apiModule = module {
    factory {
        VersionApi(
            networkCallFactory = get()
        )
    }
}