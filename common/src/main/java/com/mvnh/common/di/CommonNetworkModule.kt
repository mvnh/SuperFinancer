package com.mvnh.common.di

import com.mvnh.common.data.network.interceptor.ArticlesInterceptor
import com.mvnh.common.data.network.interceptor.MarketInterceptor
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val commonNetworkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
    }

    single { MarketInterceptor() }
    single { ArticlesInterceptor() }
}
