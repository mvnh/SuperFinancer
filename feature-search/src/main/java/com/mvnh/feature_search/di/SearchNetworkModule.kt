package com.mvnh.feature_search.di

import com.mvnh.common.data.network.interceptor.ArticlesInterceptor
import com.mvnh.common.data.network.interceptor.MarketInterceptor
import com.mvnh.common.di.components.bind
import com.mvnh.common.di.components.provideOkHttpClient
import com.mvnh.common.di.components.provideRetrofit
import com.mvnh.feature_search.data.network.api.ArticleSearchApi
import com.mvnh.feature_search.data.network.api.SymbolLookupApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

val searchNetworkModule = module {
    factory(named("search_articles")) { provideOkHttpClient(get<ArticlesInterceptor>()) }
    factory(named("search_market")) { provideOkHttpClient(get<MarketInterceptor>()) }

    factory {
        provideRetrofit(
            baseUrl = "https://api.nytimes.com/svc/search/v2/",
            client = get(named("search_articles")),
            json = get()
        ).bind<ArticleSearchApi>()
    }
    factory {
        provideRetrofit(
            baseUrl = "https://finnhub.io/api/v1/",
            client = get(named("search_market")),
            json = get()
        ).bind<SymbolLookupApi>()
    }
}
