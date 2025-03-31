package com.mvnh.feature_home.di

import com.mvnh.feature_home.data.repository.IArticlesRepository
import com.mvnh.feature_home.data.repository.IMarketRepository
import com.mvnh.feature_home.domain.repository.ArticlesRepository
import com.mvnh.feature_home.domain.repository.MarketRepository
import org.koin.dsl.module

val homeRepositoryModule = module {
    factory<MarketRepository> { IMarketRepository(get(), get()) }
    factory<ArticlesRepository> { IArticlesRepository(get(), get()) }
}
