package com.mvnh.feature_home.di

import com.mvnh.common.data.network.api.MarketApi
import com.mvnh.common.data.network.interceptor.ArticlesInterceptor
import com.mvnh.common.data.network.interceptor.MarketInterceptor
import com.mvnh.common.di.components.bind
import com.mvnh.common.di.components.provideOkHttpClient
import com.mvnh.common.di.components.provideRetrofit
import com.mvnh.feature_home.data.network.api.ArticlesApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeNetworkModule = module {
    factory(named("market")) { provideOkHttpClient(get<MarketInterceptor>()) }
    factory(named("news")) { provideOkHttpClient(get<ArticlesInterceptor>()) }

    factory {
        provideRetrofit(
            baseUrl = "https://finnhub.io/api/v1/",
            client = get(named("market")),
            json = get()
        ).bind<MarketApi>()
    }
    factory {
        provideRetrofit(
            baseUrl = "https://api.nytimes.com/svc/",
            client = get(named("news")),
            json = get()
        ).bind<ArticlesApi>()
    }
}
